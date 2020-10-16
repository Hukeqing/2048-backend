package com.mauve.tzfe.mapper;

import com.mauve.tzfe.model.entity.Friend;
import com.mauve.tzfe.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FriendMapper {
    @Select("SELECT * FROM user WHERE id in (SELECT b FROM friend WHERE a=#{id} AND status=0 UNION SELECT a FROM friend WHERE b=#{id} AND status=0)")
    List<User> getUserFriendsById(@Param("id") Integer id);

    @Select("SELECT * FROM user WHERE id in (SELECT a FROM friend WHERE b=#{id} AND status=1)")
    List<User> getUserFriendRequestById(@Param("id") Integer id);

    @Select("SELECT * FROM friend WHERE (a=#{a} AND b=#{b}) OR (a=#{b} AND b=#{a})")
    Friend checkFriend(@Param("a") Integer a, @Param("b") Integer b);

    @Insert("INSERT INTO friend (a, b, status) VALUE (#{a}, #{b}, 1)")
    void insertFriend(@Param("a") Integer a, @Param("b") Integer b);

    @Update("UPDATE friend SET status=0 WHERE id=#{id}")
    void updateFriend(@Param("id") Integer id);

    @Delete("DELETE FROM friend WHERE id=#{id}")
    void deleteFriend(@Param("id") Integer id);
}
