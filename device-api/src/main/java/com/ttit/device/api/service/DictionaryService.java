package com.ttit.device.api.service;

import com.ttit.device.api.entity.Dictionary;

import java.util.List;

/**
 * Description:
 *
 * @author 小谢
 * Date: 2019/5/2115:11
 */
public interface DictionaryService {
    List<Dictionary> listForDict();

    List<Dictionary> listByType(String type);

    List<Dictionary> list(String type);
}
