package com.mauve.tzfe.controller;

import com.mauve.tzfe.model.Response;
import com.mauve.tzfe.model.entity.User;
import com.mauve.tzfe.model.request.*;
import com.mauve.tzfe.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Api(value = "用户", tags = {"用户接口"})
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Response<User> login(@RequestBody LoginRequest request, HttpServletRequest header) {
        User curUser = userService.getUserByAccount(request.getAccount());
        if (curUser == null) return Response.fail("账号不存在");
        if (!curUser.getPassword().equals(request.getPassword())) return Response.fail("密码错误");
        userService.updateUserSession(header.getSession().toString(), curUser.getId());
        return Response.success(curUser);
    }

    @PostMapping("/check")
    public Response<User> check(HttpServletRequest header) {
        User curUser = userService.checkUser(header.getSession().toString());
        if (curUser == null) return Response.fail("未登录");
        return Response.success(curUser);
    }

    @PostMapping("/logout")
    public Response<Boolean> logout(HttpServletRequest header) {
        User curUser = userService.checkUser(header.getSession().toString());
        if (curUser == null) return Response.fail("未登录");
        userService.updateUserSession("", curUser.getId());
        return Response.success(true);
    }

    @PostMapping("/register")
    public Response<Boolean> register(@RequestBody RegisterRequest request) {
        if (userService.registerNewAccount(request.getAccount(), request.getPassword(), request.getNick()))
            return Response.success(true);
        return Response.fail("邮箱被占用");
    }

    @PostMapping("/changePassword")
    public Response<Boolean> changePassword(@ApiParam(value = "新的密码") @RequestBody ChangePasswordRequest request, HttpServletRequest header) {
        User curUser = userService.checkUser(header.getSession().toString());
        if (curUser == null) return Response.fail("未登录");
        userService.changeUserPassword(request.getPassword(), curUser.getId());
        return Response.success(true);
    }

    @PostMapping("/newScore")
    @ApiOperation(value = "修改记录分数", notes = "返回时，返回现在的最高分，即如果发送的分数比原来的低，则会返回原来的分数，反之返回新的分数")
    public Response<Integer> newScore(@RequestBody NewScoreRequest request, HttpServletRequest header) {
        User curUser = userService.checkUser(header.getSession().toString());
        if (curUser == null) return Response.fail("未登录");
        if (curUser.getHighest() > request.getScore()) return Response.success(curUser.getHighest());
        userService.setNewScore(curUser.getId(), request.getScore());
        return Response.success(request.getScore());
    }

    @PostMapping("/game")
    public Response<Boolean> saveGame(@RequestBody GameRequest request, HttpServletRequest header) {
        User curUser = userService.checkUser(header.getSession().toString());
        if (curUser == null) return Response.fail("未登录");
        userService.setLastGame(curUser.getId(), request.getGame());
        return Response.success(true);
    }
}
