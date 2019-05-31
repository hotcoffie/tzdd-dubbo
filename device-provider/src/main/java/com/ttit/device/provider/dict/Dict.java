package com.ttit.device.provider.dict;

import com.ttit.device.api.entity.Dictionary;
import com.ttit.device.api.enums.DictTypeEnum;

import java.util.List;

/**
 * Description:
 *
 * @author 小谢
 * Date: 2019/5/2210:44
 */
public interface Dict {
    void init(List<Dictionary> list);

    String get(String type, String key);

    String get(DictTypeEnum type, String key);
}
