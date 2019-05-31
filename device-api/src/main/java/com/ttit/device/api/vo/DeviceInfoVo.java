package com.ttit.device.api.vo;

import com.ttit.device.api.entity.DeviceInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Description:
 *
 * @author 小谢
 * Date: 2019/5/228:32
 */
@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "设备信息")
public class DeviceInfoVo extends DeviceInfo {
    @ApiModelProperty(value = "设备状态名" )
    private String deviceStatusName;
    @ApiModelProperty(value = "设备分组名" )
    private String groupName;
}
