package com.mauve.tzfe.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangeNickRequest {
    @NotNull
    @ApiModelProperty("新的昵称")
    private String nick;
}
