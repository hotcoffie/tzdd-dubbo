package com.ttit.soft.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.ttit.common.Constant;
import com.ttit.common.entity.Attachment;
import com.ttit.common.utils.FileUploadUtils;
import com.ttit.common.vo.ResultVo;
import com.ttit.soft.api.entity.SoftInfo;
import com.ttit.soft.api.service.AttachmentService;
import com.ttit.soft.api.service.SoftInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description: 管理-软件信息
 *
 * @author 小谢
 * Date: 2019/5/2317:10
 */
@RestController
@RequestMapping("/soft")
@Slf4j
@Api(tags = "软件信息")
public class SoftInfoController {
    @Reference(interfaceClass = SoftInfoService.class)
    private SoftInfoService softInfoDubboService;

    @Reference(interfaceClass = AttachmentService.class)
    private AttachmentService attachmentDubboService;

    /**
     * 文件上传路径
     */
    @Value("${business.file-upload-path}")
    private String fileUploadPath;

    @GetMapping("page")
    @ApiOperation(value = "分页查询文件列表", notes = "")
    public ResultVo searchPage(
            @ApiParam(value = "软件所属类型编码") @RequestParam(required = false) String softType, @ApiParam(value = "搜索关键字") @RequestParam(required = false) String keyword,
            @ApiParam(value = "当前页码，为空/小于0不分页", required = true, example = "1") @RequestParam Integer pageNum,
            @ApiParam(value = "每页容量，为空/小于0不分页", required = true, example = "10") @RequestParam Integer pageSize,
            @ApiParam(value = "排序方式，为空不排序") @RequestParam(required = false) String orderBy) {
        PageInfo page = softInfoDubboService.searchPage(softType, keyword, pageNum, pageSize, orderBy);
        return ResultVo.success(page);
    }

    @PostMapping("upload")
    @ApiOperation(value = "上传软件", notes = "")
    public ResultVo upload(@ApiParam(value = "软件所属类型编码") @RequestParam String softType,
                           @ApiParam(value = "上传的软件") MultipartFile webFile) {
        String userId = Constant.USER_ID_ADMIN;
        Attachment attachment = FileUploadUtils.uploud(webFile, fileUploadPath, userId);

        //3.持久化软件信息
        attachment = attachmentDubboService.add(attachment);
        SoftInfo info = softInfoDubboService.add(softType, attachment, userId);
        return ResultVo.success(info);
    }


    @DeleteMapping("del/{id}")
    @ApiOperation(value = "从列表中逻辑删除指定设备分组", notes = "")
    public ResultVo del(@ApiParam(value = "设备分组ID") @PathVariable String id) {
        //用户体系暂未规划，这里默认用1
        SoftInfo info = softInfoDubboService.del(id);
        return ResultVo.success(info);
    }
}
