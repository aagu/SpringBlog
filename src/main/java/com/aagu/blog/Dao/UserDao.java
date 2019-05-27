package com.aagu.blog.Dao;

import com.aagu.blog.Models.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
@CacheNamespace
public interface UserDao {
    @Select("select * from user where name=#{name}")
    User getByName(@Param("name") String name);
}
