package com.aagu.blog.Dao;

import com.aagu.blog.Models.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentDao {

    @Select("select * from comment")
    List<Comment> getAll();

    @Select("select * from comment where articleId=#{articleId}")
    List<Comment> getByArticle(@Param("articleId") Integer articleId);
}
