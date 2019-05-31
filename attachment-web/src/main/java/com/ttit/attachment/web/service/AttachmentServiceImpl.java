package com.ttit.attachment.web.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.ttit.attachment.api.entity.Attachment;
import com.ttit.attachment.api.service.AttachmentDubboService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description:
 *
 * @author 小谢
 * Date: 2019/5/2414:10
 */
@Service
@Slf4j
public class AttachmentServiceImpl implements AttachmentService {
    @Reference(interfaceClass = AttachmentDubboService.class, version = "1.0.0")
    private AttachmentDubboService attachmentDubboService;

    @Override
    public PageInfo searchPage(String keyword, Integer pageNum, Integer pageSize, String orderBy) {
        return attachmentDubboService.searchPage(keyword, pageNum, pageSize, orderBy);
    }

    @Override
    public Attachment upload(MultipartFile webFile, String userId) {
        return attachmentDubboService.upload(webFile, userId);
    }

    @Override
    public ResponseEntity<FileSystemResource> download(String id) {
        return attachmentDubboService.download(id);
    }

    /*
    @Override
    Attachment del(String id, String userId) {
        return attachmentDubboService.del(id, userId);
    }
    */
}
