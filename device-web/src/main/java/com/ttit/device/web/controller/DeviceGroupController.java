package com.ttit.device.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.ttit.common.Constant;
import com.ttit.common.vo.ResultVo;
import com.ttit.device.api.entity.DeviceGroup;
import com.ttit.device.api.service.DeviceGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * Description: 管理-设备分组信息
 *
 * @author 小谢
 * Date: 2019/5/2113:50
 */
@RestController
@RequestMapping("/group")
@Api(tags = "设备分组管理")
public class DeviceGroupController {
    @Reference(interfaceClass = DeviceGroupService.class)
    private DeviceGroupService deviceGroupDubboService;
    @GetMapping("page")
    @ApiOperation(value = "分页查询设备信息列表", notes = "")
    public ResultVo searchPage(@ApiParam(value = "搜索关键字") @RequestParam(required = false) String keyword,
                               @ApiParam(value = "当前页码，为空/小于0不分页", required = true, example = "1") @RequestParam Integer pageNum,
                               @ApiParam(value = "每页容量，为空/小于0不分页", required = true, example = "10") @RequestParam Integer pageSize,
                               @ApiParam(value = "排序方式，为空不排序") @RequestParam(required = false) String orderBy) {
        PageInfo page = deviceGroupDubboService.searchPage(keyword, pageNum, pageSize, orderBy);
        return ResultVo.success(page);
    }

    @PutMapping("add")
    @ApiOperation(value = "新增设备分组", notes = "")
    public ResultVo add(@RequestBody DeviceGroup deviceGroup) {
        DeviceGroup group = deviceGroupDubboService.add(deviceGroup, Constant.USER_ID_ADMIN);
        return ResultVo.success(group);
    }

    @DeleteMapping("del/{id}")
    @ApiOperation(value = "从列表中逻辑删除指定设备分组", notes = "")
    public ResultVo del(@ApiParam(value = "设备分组ID") @PathVariable String id) {
        //用户体系暂未规划，这里默认用1
        DeviceGroup group = deviceGroupDubboService.del(id, Constant.USER_ID_ADMIN);
        return ResultVo.success(group);
    }

    @PostMapping("modify")
    @ApiOperation(value = "修改设备分组信息", notes = "")
    public ResultVo modify(@ApiParam(value = "设备分组信息") @RequestBody DeviceGroup deviceGroup) {
        DeviceGroup group = deviceGroupDubboService.modify(deviceGroup, Constant.USER_ID_ADMIN);
        return ResultVo.success(group);
    }

}
