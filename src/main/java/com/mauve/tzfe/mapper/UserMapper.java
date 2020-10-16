package com.mauve.tzfe.mapper;

import com.mauve.tzfe.model.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE account=#{account}")
    User selectUserByAccount(@Param("account") String account);

    @Select("SELECT * FROM user WHERE id=#{id}")
    User selectUserById(@Param("id") Integer id);

    @Select("SELECT * FROM user WHERE session=#{session}")
    User selectUserBySession(@Param("session") String session);

    @Insert("INSERT INTO user (account, password, nick) VALUE (#{account}, #{password}, #{nick})")
    void insertUser(@Param("account") String account, @Param("password") String password, @Param("nick") String nick);

    @Update("UPDATE user SET highest=#{score} WHERE id=#{id}")
    void updateScore(@Param("id") Integer id, @Param("score") Integer score);

    @Update("UPDATE user SET lastGame=#{game} WHERE id=#{id}")
    void updateGame(@Param("id") Integer id, @Param("game") String game);

    @Update("UPDATE user SET session=#{session} WHERE id=#{id}")
    void updateSession(@Param("session") String session, @Param("id") Integer id);
}
