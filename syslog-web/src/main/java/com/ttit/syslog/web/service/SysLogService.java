package com.ttit.syslog.web.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.ttit.syslog.api.service.SysLogDubboService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Description: 系统日志
 *
 * @author 小谢
 * Date: 2019/5/2311:08
 */
@Component
@Slf4j
public class SysLogService {
    @Reference(version = "1.0.0")
    private SysLogDubboService sysLogDubboService;

    public PageInfo searchPage(String sysLogType, String keyword, Integer pageNum, Integer pageSize, String orderBy) {
        return sysLogDubboService.searchPage(sysLogType, keyword, pageNum, pageSize, sysLogType);
    }
}
