package com.mauve.tzfe.controller;

import com.mauve.tzfe.model.Response;
import com.mauve.tzfe.model.entity.User;
import com.mauve.tzfe.model.request.AddFriendRequest;
import com.mauve.tzfe.model.request.DealRequest;
import com.mauve.tzfe.service.FriendService;
import com.mauve.tzfe.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/friend")
@RestController
public class FriendController {
    @Resource
    private FriendService friendService;

    @Resource
    private UserService userService;

    @GetMapping("/getList")
    public Response<List<User>> getFriendList(HttpServletRequest header) {
        User curUser = userService.checkUser(header.getSession().toString());
        if (curUser == null) return Response.fail("未登录");
        return Response.success(friendService.getFriendsList(curUser.getId()));
    }

    @GetMapping("/getRequest")
    public Response<List<User>> getFriendRequest(HttpServletRequest header) {
        User curUser = userService.checkUser(header.getSession().toString());
        if (curUser == null) return Response.fail("未登录");
        return Response.success(friendService.getFriendRequest(curUser.getId()));
    }

    @PostMapping("/addFriend")
    public Response<Boolean> addFriend(@RequestBody AddFriendRequest request, HttpServletRequest header) {
        User curUser = userService.checkUser(header.getSession().toString());
        if (curUser == null) return Response.fail("未登录");
        User toUser = userService.getUserByAccount(request.getAccount());
        if (toUser == null) return Response.fail("用户不存在");
        String res = friendService.addFriend(curUser.getId(), toUser.getId());
        if (res.isEmpty()) return Response.success(true);
        return Response.fail(res);
    }

    @PostMapping("/acceptFriend")
    public Response<Boolean> acceptFriend(@RequestBody DealRequest request, HttpServletRequest header) {
        User curUser = userService.checkUser(header.getSession().toString());
        String res = friendService.dealFriend(curUser.getId(), request.getId(), true);
        if (res.isEmpty()) return Response.success(true);
        return Response.fail(res);
    }

    @PostMapping("/rejectFriend")
    public Response<Boolean> rejectFriend(@RequestBody DealRequest request, HttpServletRequest header) {
        User curUser = userService.checkUser(header.getSession().toString());
        String res = friendService.dealFriend(curUser.getId(), request.getId(), false);
        if (res.isEmpty()) return Response.success(true);
        return Response.fail(res);
    }

    @PostMapping("/deleteFriend")
    public Response<Boolean> deleteFriend(@RequestBody DealRequest request, HttpServletRequest header) {
        User curUser = userService.checkUser(header.getSession().toString());
        String res = friendService.deleteFriend(curUser.getId(), request.getId());
        if (res.isEmpty()) return Response.success(true);
        return Response.fail(res);
    }
}
