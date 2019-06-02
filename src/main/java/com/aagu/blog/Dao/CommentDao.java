package com.aagu.blog.Dao;

import com.aagu.blog.Models.Comment;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
@CacheNamespace
public interface CommentDao {

    @Select("select * from comment")
    List<Comment> getAll();

    @Select("select * from comment where articleId=#{articleId}")
    List<Comment> getByArticle(@Param("articleId") Integer articleId);

    @SelectProvider(type = CommentSqlBuilder.class, method = "buildSearch")
    @Results({
            @Result(property = "articleTitle", column = "title"),
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "createtime"),
    })
    List<Comment> getByPage(@Param("start") Integer start,
                            @Param("num") Integer end,
                            @Param("search") String search,
                            @Param("order") String order);

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

    class CommentSqlBuilder {
        public static String buildSearch(Map<String, Object> param) {
            return new SQL(){{
                SELECT("comment.detail", "email", "title", "comment.id", "articleId", "createtime");
                FROM("comment");
                JOIN(" article on comment.articleId = article.id");
                if (param.get("search") != null) {
                    WHERE("comment.detail like '%" + param.get("search") + "%'");
                }
                ORDER_BY("createtime " + param.get("order"));
            }}.toString() + " LIMIT " + param.get("start") + "," + param.get("num");
        }
    }
}
