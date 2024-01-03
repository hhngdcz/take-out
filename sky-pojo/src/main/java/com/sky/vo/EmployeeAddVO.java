package com.sky.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "员工添加时传递的数据模型")
public class EmployeeAddVO implements Serializable {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("员工姓名")
    private String employeeName;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("性别")
    private Integer sex;

    @ApiModelProperty("身份证号")
    private String idNumber;

}
