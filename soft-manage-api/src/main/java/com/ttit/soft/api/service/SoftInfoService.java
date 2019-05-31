package com.ttit.soft.api.service;

import com.github.pagehelper.PageInfo;
import com.ttit.common.entity.Attachment;
import com.ttit.soft.api.entity.SoftInfo;

/**
 * Description:软件信息
 *
 * @author 小谢
 * Date: 2019/5/2317:16
 */
public interface SoftInfoService {
    SoftInfo add(String softType, Attachment attachment, String userId);

    SoftInfo del(String id);

    PageInfo searchPage(String softType, String keyword, Integer pageNum, Integer pageSize, String orderBy);

}
