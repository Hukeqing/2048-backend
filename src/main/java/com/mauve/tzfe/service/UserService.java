package com.mauve.tzfe.service;

import com.mauve.tzfe.mapper.UserMapper;
import com.mauve.tzfe.model.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public User getUserById(Integer id) {
        return userMapper.selectUserById(id);
    }

    public User getUserByAccount(String account) {
        return userMapper.selectUserByAccount(account);
    }

    public User checkUser(String session) {
        return userMapper.selectUserBySession(session);
    }

    public void updateUserSession(String session, Integer id) {
        userMapper.updateSession(session, id);
    }

    public void changeUserPassword(String password, Integer id) {
        userMapper.updatePassword(id, password);
    }

    public Boolean registerNewAccount(String account, String password, String nick) {
        if (getUserByAccount(account) != null) return false;
        userMapper.insertUser(account, password, nick);
        return true;
    }

    public void setNewScore(Integer id, Integer newScore) {
        userMapper.updateScore(id, newScore);
    }

    public void setLastGame(Integer id, String game) {
        userMapper.updateGame(id, game);
    }
}
