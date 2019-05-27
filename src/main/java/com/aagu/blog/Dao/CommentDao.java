package com.aagu.blog.Dao;

import com.aagu.blog.Models.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
@CacheNamespace
public interface CommentDao {

    @Select("select * from comment")
    List<Comment> getAll();

    @Select("select * from comment where articleId=#{articleId}")
    List<Comment> getByArticle(@Param("articleId") Integer articleId);

    @Select("select comment.detail, email, title, comment.id, articleId, createtime " +
            "from comment join article on comment.articleId = article.id " +
            "limit #{start}, #{end}")
    @Results({
            @Result(property = "articleTitle", column = "title"),
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "createtime"),
    })
    List<Comment> getByPage(@Param("start") Integer start, @Param("end") Integer end);

    @Select("select comment.detail, email, title, comment.id, articleId, createtime from comment join article on comment.articleId = article.id" +
            " where isRead=0")
    @Results({
            @Result(property = "articleTitle", column = "title"),
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "createtime"),
    })
    List<Comment> getUnread();

    @Select("select ceil(count(id)/#{div}) from comment")
    Integer getCommentCount(@Param("div") Integer div);

    @Insert("insert into comment(detail, email, articleId) value(#{detail},#{email},#{id})")
    Integer insertComment(@Param("detail") String detail, @Param("email") String email, @Param("id") Integer articleId);

    @Delete("delete from comment where id=#{id}")
    Integer deleteById(@Param("id") Integer id);

    @Update("update comment set isRead=1 where id=#{id}")
    Integer markAsRead(@Param("id") Integer id);
}
