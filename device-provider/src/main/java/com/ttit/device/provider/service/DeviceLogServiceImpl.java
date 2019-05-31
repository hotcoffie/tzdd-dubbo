package com.ttit.device.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ttit.common.exceptions.BusinessException;
import com.ttit.common.exceptions.NotNullException;
import com.ttit.device.api.entity.DeviceInfo;
import com.ttit.device.api.entity.DeviceLog;
import com.ttit.device.api.enums.DictTypeEnum;
import com.ttit.device.api.exceptions.DeviceException;
import com.ttit.device.api.service.DeviceLogService;
import com.ttit.device.api.vo.DeviceLogVo;
import com.ttit.device.provider.dao.DeviceInfoDao;
import com.ttit.device.provider.dao.DeviceLogDao;
import com.ttit.device.provider.dict.DictHadler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Description: 设备日志
 *
 * @author 小谢
 * Date: 2019/5/239:32
 */
@Service(interfaceClass = DeviceLogService.class)
@Component
public class DeviceLogServiceImpl implements DeviceLogService {
    @Resource
    private DeviceLogDao deviceLogDao;
    @Resource
    private DeviceInfoDao deviceInfoDao;
    @Resource
    private DictHadler dictHadler;

    @Override
    public PageInfo searchPage(String devLogType, String keyword, Integer pageNum, Integer pageSize, String orderBy) {
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<DeviceLogVo> list = deviceLogDao.searchPage(devLogType, keyword);
        //遍历翻译日志类型
        list.forEach(devLog -> {
            String logTypeName = dictHadler.get(DictTypeEnum.devLogType, devLog.getDevLogType());
            devLog.setDevLogTypeName(logTypeName);
        });
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeviceLog addLog(DeviceLog log) {
        if (log == null) {
            throw new NotNullException();
        }
        String devSerialNum = log.getDevSerialNum();
        DeviceInfo deviceInfo = deviceInfoDao.findBySerialNum(devSerialNum);
        if (deviceInfo == null) {
            throw new BusinessException("不存在串号[" + devSerialNum + "]的设备！");
        }
        deviceLogDao.add(log);
        return log;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeviceLog addLog(String devSerialNum, String devLogType, String content, Date createTime) {
        DeviceLog log = new DeviceLog(devSerialNum, devLogType, content, createTime);
        return addLog(log);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeviceLog addLog(DeviceException e) {
        DeviceLog log = new DeviceLog(e);
        return addLog(log);
    }

}
