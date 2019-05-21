package com.aagu.blog.Dao;

import com.aagu.blog.Models.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    @Select("select * from user where name=#{name}")
    User getByName(@Param("name") String name);
}
