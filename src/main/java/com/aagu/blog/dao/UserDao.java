package com.aagu.blog.dao;

import com.aagu.blog.model.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
@CacheNamespace
public interface UserDao {
    @Select("select id, name, email, status, avatar from user where name=#{name}")
    User getByName(@Param("name") String name);

    @Select("select password from user where name=#{name}")
    String getPassword(@Param("name") String name);

    @Select("select id, name, email, status, avatar from user limit #{start}, #{offset}")
    List<User> getByPage(@Param("start")Integer page, @Param("offset")Integer num);

    @Select("select name from user where name like #{name}")
    List<String> getAllNameLike(@Param("name")String name);
}
