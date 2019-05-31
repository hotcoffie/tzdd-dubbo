package com.ttit.device.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ttit.common.Constant;
import com.ttit.common.exceptions.NotExistException;
import com.ttit.common.exceptions.NotNullException;
import com.ttit.common.utils.UuidUtils;
import com.ttit.device.api.entity.DeviceGroup;
import com.ttit.device.api.service.DeviceGroupService;
import com.ttit.device.provider.dao.DeviceGroupDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:
 *
 * @author 小谢
 * Date: 2019/5/2316:02
 */
@Service(interfaceClass = DeviceGroupService.class)
@Component
public class DeviceGroupServiceImpl implements DeviceGroupService {
    @Resource
    private DeviceGroupDao deviceGroupDao;

    @Override
    public PageInfo searchPage(String keyword, Integer pageNum, Integer pageSize, String orderBy) {
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<DeviceGroup> list = deviceGroupDao.searchPage(keyword);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeviceGroup add(DeviceGroup deviceGroup, String userId) {
        //1.数据校验
        if (deviceGroup == null) {
            throw new NotNullException();
        }

        //2.固定项配置
        String id = UuidUtils.generate();
        deviceGroup.setId(id);
        deviceGroup.setCreator(userId);

        //3.持久化
        deviceGroupDao.add(deviceGroup);

        return deviceGroupDao.findById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeviceGroup del(String id, String userId) {
        //1.数据校验
        if (StringUtils.isBlank(id)) {
            throw new NotNullException();
        }
        DeviceGroup deviceGroup = deviceGroupDao.findById(id);
        if (deviceGroup == null || Constant.IS_DEL.equals(deviceGroup.getIsDel())) {
            throw new NotExistException("设备分组ID:" + id);
        }

        deviceGroupDao.del(id);
//TODO:注意分组中设备的处理

        return deviceGroup;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeviceGroup modify(DeviceGroup deviceGroup, String userId) {
        //1.数据校验
        if (deviceGroup == null) {
            throw new NotNullException();
        }
        String id = deviceGroup.getId();
        if (StringUtils.isBlank(id)) {
            throw new NotNullException();
        }
        DeviceGroup deviceGroupTest = deviceGroupDao.findById(id);
        if (deviceGroupTest == null || Constant.IS_DEL.equals(deviceGroupTest.getIsDel())) {
            throw new NotExistException("设备分组ID:" + id);
        }

        //2.持久化
        deviceGroupDao.modify(deviceGroup);

        return deviceGroupDao.findById(id);
    }
}
