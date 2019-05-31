package com.ttit.device.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.ttit.common.vo.ResultVo;
import com.ttit.device.api.service.DeviceLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:管理-设备日志
 *
 * @author 小谢
 * Date: 2019/5/2417:18
 */
@RestController
@RequestMapping("/manager/device/log")
@Api(tags = "设备日志管理")
public class DeviceLogController {
    @Reference(interfaceClass = DeviceLogService.class)
    private DeviceLogService deviceLogDubboService;
    @GetMapping("page")
    @ApiOperation(value = "分页查询设备日志列表", notes = "")
    public ResultVo searchPage(@ApiParam(value = "日志类型编码") @RequestParam(required = false) String devLogType,
                               @ApiParam(value = "搜索关键字") @RequestParam(required = false) String keyword,
                               @ApiParam(value = "当前页码，为空/小于0不分页", required = true, example = "1") @RequestParam Integer pageNum,
                               @ApiParam(value = "每页容量，为空/小于0不分页", required = true, example = "10") @RequestParam Integer pageSize,
                               @ApiParam(value = "排序方式，为空不排序") @RequestParam(required = false) String orderBy) {
        PageInfo page = deviceLogDubboService.searchPage(devLogType, keyword, pageNum, pageSize, orderBy);
        return ResultVo.success(page);
    }
}
