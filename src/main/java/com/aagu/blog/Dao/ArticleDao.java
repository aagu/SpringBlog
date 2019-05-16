package com.aagu.blog.Dao;

import com.aagu.blog.Models.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleDao {

    @Select("select * from article")
    List<Article> getAll();

    @Select("select * from article where id=#{id}")
    Article getById(@Param("id") Integer id);

    @Select("select * from article where labelId=#{labelId}")
    List<Article> getByLabel(@Param("labelId") Integer labelId);
}
