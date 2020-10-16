package com.mauve.tzfe.controller;

import com.mauve.tzfe.model.Response;
import com.mauve.tzfe.model.entity.User;
import com.mauve.tzfe.model.request.GameRequest;
import com.mauve.tzfe.model.request.LoginRequest;
import com.mauve.tzfe.model.request.NewScoreRequest;
import com.mauve.tzfe.model.request.RegisterRequest;
import com.mauve.tzfe.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
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

    @PostMapping("/newScore")
    public Response<Boolean> newScore(@RequestBody NewScoreRequest request, HttpServletRequest header) {
        User curUser = userService.checkUser(header.getSession().toString());
        if (curUser == null) return Response.fail("未登录");
        if (curUser.getHighest() > request.getScore()) return Response.success(false);
        userService.setNewScore(curUser.getId(), request.getScore());
        return Response.success(true);
    }

    @PostMapping("/game")
    public Response<Boolean> saveGame(@RequestBody GameRequest request, HttpServletRequest header) {
        User curUser = userService.checkUser(header.getSession().toString());
        if (curUser == null) return Response.fail("未登录");
        userService.setLastGame(curUser.getId(), request.getGame());
        return Response.success(true);
    }
}
