package com.ttit.soft.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.ttit.common.entity.Attachment;
import com.ttit.common.exceptions.BusinessException;
import com.ttit.common.vo.ResultVo;
import com.ttit.soft.api.service.AttachmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Description: 附件管理
 *
 * @author 小谢
 * Date: 2019/5/2317:10
 */
@RestController
@RequestMapping("/attachment")
@Slf4j
@Api(tags = "附件管理")
public class AttachmentController {

    @Reference(interfaceClass = AttachmentService.class)
    private AttachmentService attachmentDubboService;

    @GetMapping("page")
    @ApiOperation(value = "分页查询附件列表", notes = "")
    public ResultVo searchPage(
            @ApiParam(value = "搜索关键字") @RequestParam(required = false) String keyword,
            @ApiParam(value = "当前页码，为空/小于0不分页", required = true, example = "1") @RequestParam Integer pageNum,
            @ApiParam(value = "每页容量，为空/小于0不分页", required = true, example = "10") @RequestParam Integer pageSize,
            @ApiParam(value = "排序方式，为空不排序") @RequestParam(required = false) String orderBy) {
        PageInfo page = attachmentDubboService.searchPage(keyword, pageNum, pageSize, orderBy);
        return ResultVo.success(page);
    }

    @GetMapping("download/{id}")
    @ApiOperation(value = "下载软件", notes = "")
    public ResponseEntity<FileSystemResource> download(@ApiParam(value = "软件ID") @PathVariable String id) {
        //1.获取附件信息
        Attachment attachment= attachmentDubboService.download(id);
        FileSystemResource file = new FileSystemResource(attachment.getPath());

        //2.配置头文件
        String fileName = new String(attachment.getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "soft;fileName=" + fileName);
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        //3.返回附件数据
        try {
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(file.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(file);
        } catch (IOException e) {
            log.error("文件下载失败！", e);
            throw new BusinessException("文件下载失败！");
        }
    }

    /*
    @DeleteMapping("del/{id}")
    @ApiOperation(value = "从列表中逻辑删除指定设备分组", notes = "")
    public ResultVo del(@ApiParam(value = "设备分组ID") @PathVariable String id) {
        //用户体系暂未规划，这里默认用1
        Attachment soft = attachmentDubboService.del(id, Constant.USER_ID_ADMIN);
        return ResultVo.success(soft);
    }
    */
}
