package com.mauve.tzfe.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.internal.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class User {
    @NotNull
    @ApiModelProperty("用户id")
    Integer id;
    @ApiModelProperty("用户登录的邮箱")
    String account;
    @JsonIgnore
    String password;
    @ApiModelProperty("用户的昵称")
    String nick;
    @ApiModelProperty("用户历史最高记录")
    Integer highest;
    @ApiModelProperty("用户上一次比赛的成绩")
    String lastGame;
}
