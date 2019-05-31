package com.ttit.device.api.service;

import com.github.pagehelper.PageInfo;
import com.ttit.device.api.entity.DeviceGroup;

/**
 * Description:
 *
 * @author 小谢
 * Date: 2019/5/2316:02
 */
public interface DeviceGroupService {
    PageInfo searchPage(String keyword, Integer pageNum, Integer pageSize, String orderBy);

    DeviceGroup add(DeviceGroup deviceGroup, String userId);

    DeviceGroup del(String id, String userId);

    DeviceGroup modify(DeviceGroup deviceGroup, String userId);
}
