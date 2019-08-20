package com.aagu.blog.Dao;

import com.aagu.blog.Models.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
@CacheNamespace
public interface UserDao {
    @Select("select * from user where name=#{name}")
    User getByName(@Param("name") String name);

    @Select("select * from user limit #{start}, #{offset}")
    List<User> getByPage(@Param("start")Integer page, @Param("offset")Integer num);

    @Select("select name from user where name like #{name}")
    List<String> getAllNameLike(@Param("name")String name);
}
