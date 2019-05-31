package com.ttit.soft.api.service;

import com.github.pagehelper.PageInfo;
import com.ttit.common.entity.Attachment;

/**
 * Description:
 *
 * @author 小谢
 * Date: 2019/5/2915:19
 */
public interface AttachmentDubboService {
    PageInfo searchPage(String keyword, Integer pageNum, Integer pageSize, String orderBy);

    Attachment add(Attachment attachment);

    Attachment download(String id);

}
