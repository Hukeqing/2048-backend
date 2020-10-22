package com.mauve.tzfe.service;

import com.mauve.tzfe.mapper.UserMapper;
import com.mauve.tzfe.model.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    public User checkUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length <= 0) return null;
        for (Cookie cookie : cookies) {
            if (!cookie.getName().equals("token")) continue;
            return userMapper.selectUserByToken(cookie.getValue());
        }
        return null;
    }

    public void addUserToken(HttpServletResponse response, Integer id) {
        String token = encryption(id.toString() + System.currentTimeMillis());
        Cookie cookie = new Cookie("token", token);
//        cookie.setMaxAge(24 * 3600);
        response.addCookie(cookie);
        userMapper.updateToken(token, id);
    }

    public void deleteUserToken(Integer id) {
        userMapper.updateToken("", id);
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
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
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
