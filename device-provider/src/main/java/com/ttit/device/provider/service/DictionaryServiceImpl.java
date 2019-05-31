package com.ttit.device.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ttit.device.api.entity.Dictionary;
import com.ttit.device.api.service.DictionaryService;
import com.ttit.device.provider.dao.DictionaryDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:数据字典
 *
 * @author 小谢
 * Date: 2019/5/2115:12
 */
@Service(interfaceClass = DictionaryService.class)
@Component
public class DictionaryServiceImpl implements DictionaryService {
    @Resource
    private DictionaryDao dictionaryDao;

    @Override
    public List<Dictionary> listForDict() {
        return dictionaryDao.listForDict();
    }

    @Override
    public List<Dictionary> listByType(String type) {
        return dictionaryDao.listByType(type);
    }

    @Override
    public List<Dictionary> list(String type) {
        return dictionaryDao.listByType(type);
    }

}
