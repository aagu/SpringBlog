package com.aagu.blog.Dao;

import com.aagu.blog.Models.Comment;
import com.aagu.blog.Views.CommentVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentDao {

    @Select("select * from comment")
    List<Comment> getAll();

    @Select("select * from comment where articleId=#{articleId}")
    List<CommentVO> getByArticle(@Param("articleId") Integer articleId);

    @Select("select comment.detail, email, title, comment.id, articleId from comment join article on comment.articleId = article.id")
    @Results({
            @Result(property = "articleTitle", column = "title"),
            @Result(property = "commentId", column = "id"),
    })
    List<CommentVO> getAllComment();
}
