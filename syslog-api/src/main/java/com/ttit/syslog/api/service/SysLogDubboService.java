package com.ttit.syslog.api.service;

import com.github.pagehelper.PageInfo;
import com.ttit.syslog.api.entity.SysLog;

import java.util.Date;

/**
 * Description: 系统日志
 *
 * @author 小谢
 * Date: 2019/5/239:32
 */
public interface SysLogDubboService {
    /**
     * 记录系统日志
     *
     * @param log 系统日志
     */
    SysLog addLog(SysLog log);

    /**
     * 记录系统日志
     */
    SysLog addLog(String sysLogType, String content, String userId, Date createTime);

    /**
     * 查询系统日志列表
     */
    PageInfo searchPage(String sysLogType, String keyword, Integer pageNum, Integer pageSize, String orderBy);
}
