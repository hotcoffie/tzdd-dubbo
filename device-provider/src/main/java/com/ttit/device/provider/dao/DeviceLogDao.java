package com.ttit.device.provider.dao;

import com.ttit.device.api.entity.DeviceLog;
import com.ttit.device.api.vo.DeviceLogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description:设备日志
 *
 * @author 小谢
 * Date: 2019/5/2310:05
 */
public interface DeviceLogDao {
    List<DeviceLogVo> searchPage(@Param("devLogType") String devLogType, @Param("keyword") String keyword);

    DeviceLogVo findById(@Param("id") String id);

    Integer add(DeviceLog log);

    Integer del(@Param("id") String id);

    Integer update(DeviceLog deviceLog);

}
