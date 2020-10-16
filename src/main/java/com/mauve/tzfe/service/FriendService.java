package com.mauve.tzfe.service;

import com.mauve.tzfe.mapper.FriendMapper;
import com.mauve.tzfe.model.Response;
import com.mauve.tzfe.model.entity.Friend;
import com.mauve.tzfe.model.entity.User;
import org.omg.CORBA.INTERNAL;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FriendService {
    @Resource
    private FriendMapper friendMapper;

    public List<User> getFriendsList(Integer id) {
        return friendMapper.getUserFriendsById(id);
    }

    public List<User> getFriendRequest(Integer id) {
        return friendMapper.getUserFriendRequestById(id);
    }

    public Friend checkFriend(Integer a, Integer b) {
        return friendMapper.checkFriend(a, b);
    }

    public String addFriend(Integer a, Integer b) {
        if (a.equals(b))
            return "不可以添加自己为好友";
        Friend friend = friendMapper.checkFriend(a, b);
        if (friend == null) {
            friendMapper.insertFriend(a, b);
            return "";
        }
        if (friend.getStatus() == 0)
            return "你已经拥有了此好友";
        if (friend.getA().equals(a))
            return "你已经发送过好友请求了";
        if (friend.getB().equals(a)) {
            friendMapper.updateFriend(friend.getId());
            return "";
        }
        return "未知错误";
    }

    public String dealFriend(Integer a, Integer b, Boolean flag) {
        Friend friend = friendMapper.checkFriend(a, b);
        if (friend == null || !friend.getB().equals(a)) return "系统出错，请重试";
        if (friend.getStatus() == 0) return "你已经添加了此好友";
        if (flag)
            friendMapper.updateFriend(friend.getId());
        else
            friendMapper.deleteFriend(friend.getId());
        return "";
    }

    public String deleteFriend(Integer a, Integer b) {
        Friend friend = friendMapper.checkFriend(a, b);
        if (friend == null) return "你们还不是好友关系了";
        friendMapper.deleteFriend(friend.getId());
        return "";
    }
}
