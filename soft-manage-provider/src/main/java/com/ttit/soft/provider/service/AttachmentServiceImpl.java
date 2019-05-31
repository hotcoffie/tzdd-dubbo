package com.ttit.soft.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ttit.common.Constant;
import com.ttit.common.entity.Attachment;
import com.ttit.common.exceptions.NotExistException;
import com.ttit.common.exceptions.NotNullException;
import com.ttit.soft.api.service.AttachmentService;
import com.ttit.soft.provider.dao.AttachmentDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:
 *
 * @author 小谢
 * Date: 2019/5/2414:10
 */

@Service(interfaceClass = AttachmentService.class)
@Component
@Slf4j
public class AttachmentServiceImpl implements AttachmentService {
    @Resource
    private AttachmentDao attachmentDao;

    @Override
    public PageInfo searchPage(String keyword, Integer pageNum, Integer pageSize, String orderBy) {
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<Attachment> list = attachmentDao.searchPage(keyword);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Attachment add(Attachment attachment) {
        attachmentDao.add(attachment);
        return attachment;
    }

    @Override
    public Attachment download(String id) {
        if (StringUtils.isBlank(id)) {
            throw new NotNullException();
        }
        Attachment attachment = attachmentDao.findById(id);
        if (attachment == null || Constant.IS_DEL.equals(attachment.getIsDel())) {
            throw new NotExistException("附件ID:" + id);
        }
        return attachment;
    }

//    @Override
//    public Attachment del(String id, String userId) {
//        //1.数据校验
//        Attachment soft = getAttaWithCheck(id);
//
//        attachmentDao.del(id);
//        String content = "删除附件：" + soft.toString();
//        Date now = new Date();
//        //记录系统日志
//        sysLogService.addLog(SysLogTypeEnum.del.getCode(), content, userId, now);
//
//        return soft;
//    }

}
