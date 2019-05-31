package com.ttit.soft.api.service;

import com.ttit.soft.api.entity.SoftManager;
import com.ttit.soft.api.vo.SoftManagerVo;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author 小谢
 * Date: 2019/5/2415:24
 */
public interface SoftManagerDubboService {
    List<SoftManagerVo> list();

    SoftManager add(SoftManager softManager, String userId);

    SoftManager del(String id, String userId);

    List<Map<String, String>> updateInfo();

    SoftManager modify(String id, String softId, String version, String userId);
}
