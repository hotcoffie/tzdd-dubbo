package com.ttit.soft.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ttit.common.Constant;
import com.ttit.common.entity.Attachment;
import com.ttit.common.exceptions.NotExistException;
import com.ttit.common.exceptions.NotNullException;
import com.ttit.common.utils.UuidUtils;
import com.ttit.soft.api.entity.SoftInfo;
import com.ttit.soft.api.service.SoftInfoDubboService;
import com.ttit.soft.provider.dao.SoftInfoDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:软件信息
 *
 * @author 小谢
 * Date: 2019/5/2317:17
 */
@Service(interfaceClass = SoftInfoDubboService.class)
@Component
@Slf4j
public class SoftInfoServiceImpl implements SoftInfoDubboService {
    @Resource
    private SoftInfoDao softInfoDao;

    @Value("${business.file-download-path}")
    private String fileDownloadPath;

    @Override
    public PageInfo searchPage(String softType, String keyword, Integer pageNum, Integer pageSize, String orderBy) {
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<SoftInfo> list = softInfoDao.searchPage(softType, keyword);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SoftInfo add(String softType, Attachment attachment,String userId) {
        //拼接附件下载路径
        String url = fileDownloadPath + attachment.getId();
        SoftInfo softInfo = new SoftInfo(UuidUtils.generate(), softType, attachment.getName(), url, userId);
        softInfoDao.add(softInfo);

        return softInfoDao.findById(softInfo.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SoftInfo del(String id) {
        //1.数据校验
        if (StringUtils.isBlank(id)) {
            throw new NotNullException();
        }
        SoftInfo softInfo = softInfoDao.findById(id);
        if (softInfo == null || Constant.IS_DEL.equals(softInfo.getIsDel())) {
            throw new NotExistException("文件ID:" + id);
        }
        softInfoDao.del(id);

        return softInfo;
    }

}
