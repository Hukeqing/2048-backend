package com.mauve.tzfe.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangePasswordRequest {
    @NotNull
    @ApiModelProperty(value = "旧密码", required = true)
    private String password;

    @NotNull
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;
}
