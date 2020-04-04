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
    @Results({
            @Result(property = "articleTitle", column = "title"),
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "createtime"),
    })
    List<Comment> getAll();

    @Select("select * from comment where articleId=#{articleId}")
    @Results({
            @Result(property = "articleTitle", column = "title"),
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "createtime"),
    })
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
                            @Param("order") String order,
                            @Param("status") String status);

    @SelectProvider(type = CommentSqlBuilder.class, method = "buildPage")
    @Results({
            @Result(property = "date", column = "createtime"),
    })
    List<Comment> getByPageV2(Map<String, String> param);

    @Select("select comment.detail, email, title, comment.id, articleId, createtime, comment.status from comment join article on comment.articleId = article.id")
    @Results({
            @Result(property = "articleTitle", column = "title"),
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "createtime"),
    })
    List<Comment> getUnread();

    @Select("select ceil(count(id)/#{div}) from comment")
    Integer getCount(@Param("div") Integer div);

    @SelectProvider(type = CommentSqlBuilder.class, method = "buildCount")
    Integer getCountV2(Map<String, String> params);

    @Insert("insert into comment(detail, email, articleId) value(#{detail},#{email},#{id})")
    Integer insertComment(@Param("detail") String detail, @Param("email") String email, @Param("id") Integer articleId);

    @Update("update comment set status='deleted' where id=#{id}")
    Integer deleteById(@Param("id") Integer id);

    @Update("update comment set status='read' where id=#{id}")
    Integer markAsRead(@Param("id") Integer id);

    class CommentSqlBuilder {
        public static String buildSearch(Map<String, Object> param) {
            return new SQL(){{
                SELECT("comment.detail", "email", "title", "comment.id", "articleId", "createtime", "comment.status");
                FROM("comment");
                JOIN(" article on comment.articleId = article.id");
                if (param.get("search") != null) {
                    WHERE("comment.detail like '%" + param.get("search") + "%'");
                }
                if (param.get("status") != null) {
                    WHERE("comment.status='" + param.get("status") + "'");
                }
                ORDER_BY("createtime " + param.get("order"));
            }}.toString() + " LIMIT " + param.get("start") + "," + param.get("num");
        }

        public static String buildCount(Map<String, String> param) {
            return new SQL(){{
                if (param.containsKey("div")) {
                    SELECT("ceil(count(id)/#{div})");
                } else {
                    SELECT("count(id)");
                }
                FROM("comment");
                if (param.containsKey("articleId")) {
                    WHERE("articleId=" + Integer.parseInt(param.get("articleId")));
                }
                if (param.containsKey("status_not")) {
                    WHERE("status != '" + param.get("status_not") + "'");
                }
                if (param.containsKey("status")) {
                    WHERE("status = '" + param.get("status") + "'");
                }
            }}.toString();
        }

        public static String buildPage(Map<String, String> param) {
            return new SQL(){{
                SELECT("detail, email, id, articleId, createtime, status");
                FROM("comment");
                if (param.containsKey("status")) {
                    WHERE("comment.status='" + param.get("status") + "'");
                }
                if (param.containsKey("articleId")) {
                    WHERE("articleId=" + param.get("articleId"));
                }
                if (param.containsKey("status_not")) {
                    WHERE("status != '" + param.get("status_not") + "'");
                }
                if (param.containsKey("status")) {
                    WHERE("status = '" + param.get("status") + "'");
                }
                if (param.containsKey("order")) {
                    ORDER_BY("createtime " + param.get("order"));
                }
            }}.toString() + " LIMIT " + param.get("start") + "," + param.get("num");
        }
    }
}
