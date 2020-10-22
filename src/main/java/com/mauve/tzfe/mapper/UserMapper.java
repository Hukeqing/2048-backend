package com.mauve.tzfe.mapper;

import com.mauve.tzfe.model.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE account=#{account}")
    User selectUserByAccount(@Param("account") String account);

    @Select("SELECT * FROM user WHERE id=#{id}")
    User selectUserById(@Param("id") Integer id);

    @Select("SELECT * FROM user WHERE token=#{token}")
    User selectUserByToken(@Param("token") String token);

    @Insert("INSERT INTO user (account, password, nick) VALUE (#{account}, #{password}, #{nick})")
    void insertUser(@Param("account") String account, @Param("password") String password, @Param("nick") String nick);

    @Update("UPDATE user SET password=#{password} WHERE id=#{id}")
    void updatePassword(@Param("id") Integer id, @Param("password") String password);

    @Update("UPDATE user SET nick=#{nick} WHERE id=#{id}")
    void updateUserNick(@Param("id") Integer id, @Param("nick") String nick);

    @Update("UPDATE user SET highest=#{score} WHERE id=#{id}")
    void updateScore(@Param("id") Integer id, @Param("score") Integer score);

    @Update("UPDATE user SET lastGame=#{game} WHERE id=#{id}")
    void updateGame(@Param("id") Integer id, @Param("game") String game);

    @Update("UPDATE user SET token=#{token} WHERE id=#{id}")
    void updateToken(@Param("token") String token, @Param("id") Integer id);
}
