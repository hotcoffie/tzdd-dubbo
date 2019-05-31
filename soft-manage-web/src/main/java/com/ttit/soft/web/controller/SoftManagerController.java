package com.ttit.soft.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ttit.common.Constant;
import com.ttit.common.vo.ResultVo;
import com.ttit.soft.api.entity.SoftManager;
import com.ttit.soft.api.service.SoftManagerService;
import com.ttit.soft.api.vo.SoftManagerVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Description: 软件管理
 *
 * @author 小谢
 * Date: 2019/5/2415:22
 */
@RestController
@RequestMapping("/manage")
@Slf4j
@Api(tags = "软件管理")
public class SoftManagerController {

    @Reference(interfaceClass = SoftManagerService.class)
    private SoftManagerService softManagerDubboService;

    @GetMapping("list")
    @ApiOperation(value = "软件管理信息列表", notes = "")
    public ResultVo list() {
        List<SoftManagerVo> list = softManagerDubboService.list();
        return ResultVo.success(list);
    }

    @PutMapping("add")
    @ApiOperation(value = "新增软件管理信息", notes = "")
    public ResultVo add(@RequestBody SoftManager softManager) {
        SoftManager softManagerNew = softManagerDubboService.add(softManager, Constant.USER_ID_ADMIN);
        return ResultVo.success(softManagerNew);
    }

    @DeleteMapping("del/{id}")
    @ApiOperation(value = "从列表中逻辑删除指定软件管理信息", notes = "")
    public ResultVo del(@ApiParam(value = "软件分类ID") @PathVariable String id) {
        //用户体系暂未规划，这里默认用1
        SoftManager softManager = softManagerDubboService.del(id, Constant.USER_ID_ADMIN);
        return ResultVo.success(softManager);
    }

    @PostMapping("modify")
    @ApiOperation(value = "修改软件版本管理信息", notes = "")
    public ResultVo modify(@ApiParam(value = "软件分类ID") @RequestParam String id,
                           @ApiParam(value = "对应软件ID") @RequestParam String softId,
                           @ApiParam(value = "软件版本号") @RequestParam String version) {
        //用户体系暂未规划，这里默认用1
        SoftManager softManagerNew = softManagerDubboService.modify(id, softId, version, Constant.USER_ID_ADMIN);
        return ResultVo.success(softManagerNew);
    }

    @GetMapping("update")
    @ApiOperation(value = "软件更新，获取最新版软件信息", notes = "")
    public ResultVo updateInfo() {
        List<Map<String, String>> info = softManagerDubboService.updateInfo();
        return ResultVo.success(info);
    }
}
