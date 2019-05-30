package com.ttit.attachment.api.service;

import com.github.pagehelper.PageInfo;
import com.ttit.attachment.api.entity.Attachment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description:
 *
 * @author 小谢
 * Date: 2019/5/2915:19
 */
public interface AttachmentDubboService {
    PageInfo searchPage(String keyword, Integer pageNum, Integer pageSize, String orderBy);

    Attachment upload(MultipartFile webFile, String userId);

    ResponseEntity<FileSystemResource> download(String id);

}
