package com.ttit.syslog.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ttit.common.exceptions.NotNullException;
import com.ttit.syslog.api.entity.SysLog;
import com.ttit.syslog.api.service.SysLogDubboService;
import com.ttit.syslog.api.vo.SysLogVo;
import com.ttit.syslog.provider.dao.SysLogDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Description: 系统日志
 *
 * @author 小谢
 * Date: 2019/5/2311:08
 */
@Service(interfaceClass = SysLogDubboService.class, version = "1.0.0")
@Component
@Slf4j
public class SysLogServiceImpl implements SysLogDubboService {
    @Resource
    private SysLogDao sysLogDao;

    @Override
    public PageInfo searchPage(String sysLogType, String keyword, Integer pageNum, Integer pageSize, String orderBy) {
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<SysLogVo> list = sysLogDao.searchPage(sysLogType, keyword);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysLog addLog(SysLog log) {
        if (log == null) {
            throw new NotNullException();
        }
        sysLogDao.add(log);
        return log;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysLog addLog(String sysLogType, String content, String userId, Date createTime) {
        SysLog log = new SysLog(sysLogType, content, userId, createTime);
        return addLog(log);
    }
}
