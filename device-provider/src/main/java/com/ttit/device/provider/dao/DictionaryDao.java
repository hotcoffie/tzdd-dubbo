package com.ttit.device.provider.dao;

import com.ttit.device.api.entity.Dictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description:数据字典
 *
 * @author 小谢
 * Date: 2019/5/2115:13
 */
public interface DictionaryDao {
    List<Dictionary> listForDict();

    List<Dictionary> listByType(@Param("type") String type);

}
