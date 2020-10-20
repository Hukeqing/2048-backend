package com.mauve.tzfe.service;

import com.mauve.tzfe.mapper.UserMapper;
import com.mauve.tzfe.model.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
        userMapper.updatePassword(id, encryption(password));
    }

    public void changeUserNick(String nick, Integer id) {
        userMapper.updateUserNick(id, nick);
    }

    public Boolean registerNewAccount(String account, String password, String nick) {
        if (getUserByAccount(account) != null) return false;
        userMapper.insertUser(account, encryption(password), nick);
        return true;
    }

    public void setNewScore(Integer id, Integer newScore) {
        userMapper.updateScore(id, newScore);
    }

    public void setLastGame(Integer id, String game) {
        userMapper.updateGame(id, game);
    }

    private static String encryption(@NotNull String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(str.getBytes());
            return new BigInteger(messageDigest.digest()).toString(32);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public Boolean checkPassword(@NotNull String password, @NotNull String databasePwd) {
        return encryption(password).equals(databasePwd);
    }
}
