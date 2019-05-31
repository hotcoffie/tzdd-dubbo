package com.ttit.soft.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ttit.common.Constant;
import com.ttit.common.exceptions.BusinessException;
import com.ttit.common.exceptions.NotExistException;
import com.ttit.common.exceptions.NotNullException;
import com.ttit.common.utils.UuidUtils;
import com.ttit.soft.api.entity.SoftInfo;
import com.ttit.soft.api.entity.SoftManager;
import com.ttit.soft.api.service.SoftManagerDubboService;
import com.ttit.soft.api.vo.SoftManagerVo;
import com.ttit.soft.provider.dao.SoftInfoDao;
import com.ttit.soft.provider.dao.SoftManagerDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author 小谢
 * Date: 2019/5/2415:24
 */
@Service(interfaceClass = SoftManagerDubboService.class)
@Component
@Slf4j
public class SoftManagerServiceImpl implements SoftManagerDubboService {
    @Resource
    private SoftManagerDao softManagerDao;
    @Resource
    private SoftInfoDao softInfoDao;

    @Override
    public List<SoftManagerVo> list() {
        List<SoftManagerVo> managers = softManagerDao.list();
        List<SoftInfo> softs = softInfoDao.searchPage(null, null);
        //通过遍历，将当前所有软件信息附加到对应软件管理信息中，便于用户下拉切换
        managers.forEach(manager -> {
            List<SoftInfo> children = manager.getSofts();
            if (children == null) {
                children = new ArrayList<>();
            }
            String managerSoftType = manager.getSoftType();
            for (SoftInfo soft : softs) {
                String softType = soft.getSoftType();
                if (StringUtils.isBlank(softType)) {
                    continue;
                }
                if (softType.equals(managerSoftType)) {
                    children.add(soft);
                }
            }
            manager.setSofts(children);
        });
        return managers;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SoftManager add(SoftManager softManager, String userId) {
        //1.数据校验
        if (softManager == null) {
            throw new NotNullException();
        }
        String softType = softManager.getSoftType();
        if (StringUtils.isBlank(softType)) {
            throw new NotNullException();
        }
        List<SoftManager> listTest = softManagerDao.findBySoftType(softType);
        if (listTest != null && !listTest.isEmpty()) {
            throw new BusinessException("不能添加重复的软件分类！");
        }

        //2.持久化
        String id = UuidUtils.generate();
        softManager.setId(id);
        softManager.setCreator(userId);
        softManagerDao.add(softManager);

        return softManagerDao.findById(id);
    }

    @Override
    public SoftManager del(String id, String userId) {
        //1.数据校验
        if (StringUtils.isBlank(id)) {
            throw new NotNullException();
        }
        SoftManager softManager = softManagerDao.findById(id);
        if (softManager == null || Constant.IS_DEL.equals(softManager.getIsDel())) {
            throw new NotExistException("软件分类ID:" + id);
        }

        softManagerDao.del(id);
        String content = "软件分类：" + softManager.toString();
        Date now = new Date();

        return softManager;
    }

    @Override
    public List<Map<String, String>> updateInfo() {
        List<SoftManagerVo> managers = softManagerDao.list();
        return managers.stream().map(manager -> {
            Map<String, String> soft = new HashMap<>();
            soft.put("softType", manager.getSoftType());
            soft.put("typeName", manager.getTypeName());
            soft.put("version", manager.getVersion());
            soft.put("url", manager.getUrl());
            soft.put("softName", manager.getSoftName());
            return soft;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SoftManager modify(String id, String softId, String version, String userId) {
        //1.数据校验
        if (StringUtils.isBlank(id) ||
                StringUtils.isBlank(softId) ||
                StringUtils.isBlank(version)) {
            throw new NotNullException();
        }
        SoftManager softManagerTest = softManagerDao.findById(id);
        SoftInfo softInfo = softInfoDao.findById(softId);
        if (softManagerTest == null || Constant.IS_DEL.equals(softManagerTest.getIsDel())) {
            throw new NotExistException("软件分类ID:" + id);
        }
        if (softInfo == null || Constant.IS_DEL.equals(softInfo.getIsDel())) {
            throw new NotExistException("软件ID:" + id);
        }
        if (!StringUtils.equalsIgnoreCase(softInfo.getSoftType(), softManagerTest.getSoftType())) {
            throw new BusinessException("软件分类不匹配！分类编码：" + softManagerTest.getSoftType() + ",软件分类编码：" + softInfo.getSoftType());
        }

        //2.持久化
        softManagerDao.modify( id,  softId,  version);

        return softManagerDao.findById(id);
    }
}
