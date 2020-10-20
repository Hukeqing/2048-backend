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
import java.util.regex.Pattern;

@RestController
@Api(value = "用户", tags = {"用户接口"})
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Response<User> login(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest) {
        User curUser = userService.checkUser(httpServletRequest.getRequestedSessionId());
        if (curUser != null) userService.updateUserSession("", curUser.getId());
        curUser = userService.getUserByAccount(request.getAccount());
        if (curUser == null) return Response.fail("账号不存在");
//        if (!curUser.getPassword().equals(request.getPassword())) return Response.fail("密码错误");
        if (!userService.checkPassword(request.getPassword(), curUser.getPassword())) return Response.fail("密码错误");
        userService.updateUserSession(httpServletRequest.getRequestedSessionId(), curUser.getId());
        return Response.success(curUser);
    }

    @PostMapping("/check")
    public Response<User> check(HttpServletRequest httpServletRequest) {
        User curUser = userService.checkUser(httpServletRequest.getRequestedSessionId());
        if (curUser == null) return Response.fail("未登录");
        return Response.success(curUser);
    }

    @PostMapping("/logout")
    public Response<Boolean> logout(HttpServletRequest httpServletRequest) {
        User curUser = userService.checkUser(httpServletRequest.getRequestedSessionId());
        if (curUser == null) return Response.fail("未登录");
        userService.updateUserSession("", curUser.getId());
        return Response.success(true);
    }

    @PostMapping("/register")
    public Response<Boolean> register(@RequestBody RegisterRequest request) {
        String regexMail = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        if (request.getAccount().length() > 100) return Response.fail("邮箱过长，请使用长度小于100位的邮箱");
        if (request.getPassword().length() < 6) return Response.fail("密码过短，密码长度范围为6-32");
        if (request.getPassword().length() > 32) return Response.fail("密码过长，密码长度范围为6-32");
        if (request.getNick().length() < 3) return Response.fail("昵称过短，昵称长度应在3-32之间");
        if (request.getNick().length() > 32) return Response.fail("昵称过长，昵称长度应在3-32之间");
        if (!Pattern.matches(regexMail, request.getAccount())) return Response.fail("邮箱格式不符合规范");

        if (userService.registerNewAccount(request.getAccount(), request.getPassword(), request.getNick()))
            return Response.success(true);
        return Response.fail("邮箱被占用");
    }

    @PostMapping("/changePassword")
    public Response<Boolean> changePassword(@RequestBody ChangePasswordRequest request, HttpServletRequest httpServletRequest) {
        User curUser = userService.checkUser(httpServletRequest.getRequestedSessionId());
        if (curUser == null) return Response.fail("未登录");
//        if (!curUser.getPassword().equals(request.getPassword())) return Response.fail("密码错误");
        if (!userService.checkPassword(request.getPassword(), curUser.getPassword())) return Response.fail("密码错误");
        userService.changeUserPassword(request.getNewPassword(), curUser.getId());
        return Response.success(true);
    }

    @PostMapping("/changeNick")
    public Response<Boolean> changeNick(@RequestBody ChangeNickRequest request, HttpServletRequest httpServletRequest) {
        User curUser = userService.checkUser(httpServletRequest.getRequestedSessionId());
        if (curUser == null) return Response.fail("未登录");
        userService.changeUserNick(request.getNick(), curUser.getId());
        return Response.success(true);
    }

    @PostMapping("/newScore")
    @ApiOperation(value = "修改记录分数", notes = "返回时，返回现在的最高分，即如果发送的分数比原来的低，则会返回原来的分数，反之返回新的分数")
    public Response<Integer> newScore(@RequestBody NewScoreRequest request, HttpServletRequest httpServletRequest) {
        User curUser = userService.checkUser(httpServletRequest.getRequestedSessionId());
        if (curUser == null) return Response.fail("未登录");
        if (curUser.getHighest() > request.getScore()) return Response.success(curUser.getHighest());
        userService.setNewScore(curUser.getId(), request.getScore());
        return Response.success(request.getScore());
    }

    @PostMapping("/game")
    public Response<Boolean> saveGame(@RequestBody GameRequest request, HttpServletRequest httpServletRequest) {
        User curUser = userService.checkUser(httpServletRequest.getRequestedSessionId());
        if (curUser == null) return Response.fail("未登录");
        userService.setLastGame(curUser.getId(), request.getGame());
        return Response.success(true);
    }
}
