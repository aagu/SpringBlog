package com.aagu.blog.Dao;

import com.aagu.blog.Models.Article;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
@CacheNamespace
public interface ArticleDao extends BaseDao<Article>{

    @Select("select id, date, labelId, title, detail as content, status" +
            " from article")
    List<Article> getAll();

    @Select("select count(id) from article")
    Integer getArticleCount();

    @Select("select id, date, labelId, title, detail as content, status" +
            " from article where id=#{id}")
    Article getById(@Param("id") Integer id);

    @Select("select id, date, labelId, title, detail as content, status" +
            " from article" +
            " where labelId=#{labelId}" +
            " order by date desc" +
            " limit #{start}, #{num}")
    List<Article> getByLabel(@Param("labelId") Integer labelId,
                             @Param("start") Integer start,
                             @Param("num") Integer num);

    @Select("select id, date, labelId, title, detail as content, status" +
            " from article" +
            " where date > str_to_date(#{date1}, '%Y-%m-%d') and date < str_to_date(#{date2}, '%Y-%m-%d')" +
            " order by date desc" +
            " limit #{start}, #{num}")
    List<Article> getByMonth(@Param("date1") LocalDate date1,
                             @Param("date2") LocalDate date2,
                             @Param("start") Integer start,
                             @Param("num") Integer num);

    @Override
    @SelectProvider(type = ArticleSqlBuilder.class, method = "buildTotal")
    int getTotal(Map<String, Object> params);

    @Override
    @SelectProvider(type = ArticleSqlBuilder.class, method = "buildPage")
    List<Article> getItems(@Param("page") int page, @Param("limit") int limit, Map<String, Object> params);

    @SelectProvider(type = ArticleSqlBuilder.class, method = "buildPage")
    List<Article> getByPage(@Param("start") Integer start, @Param("num") Integer num);

    @Select("select id, date, labelId, title, detail as content" +
            " from article where title like #{key}" +
            " order by date desc" +
            " limit #{start}, #{num}")
    List<Article> getBySearch(@Param("start") Integer start, @Param("num") Integer num, @Param("key") String key);

    @Select("select ceil(count(id)/#{div}) from article")
    Integer getPageCount(@Param("div") Integer div);

    @Select("select ceil(count(id)/#{div}) from article where labelId=#{labelId}")
    Integer getByLabelCount(@Param("labelId") Integer labelId, @Param("div") Integer div);

    @Select("select ceil(count(a.id)/#{div}) " +
            "from (select * from article where date > str_to_date(#{date1}, '%Y-%m-%d') and date < str_to_date(#{date2}, '%Y-%m-%d')) a")
    Integer getByMonthCount(@Param("date1") LocalDate date1, @Param("date2") LocalDate date2, @Param("div")Integer div);

    @Select("select ceil(count(id)/#{div}) from article where title like #{key}")
    Integer getBySearchCount(@Param("key") String key, @Param("div") Integer div);

    @Select("select max(id) from article where id<#{thisId} and status='published'")
    Integer getPrevPage(@Param("thisId") Integer id);

    @Select("select min(id) from article where id>#{thisId} and status='published'")
    Integer getNextPage(@Param("thisId") Integer id);

    @Select("select max(id) from article where status='published'")
    Integer getMaxPage();

    @Select("select distinct date_format(date, '%Y %M') from article limit 5")
    List<String> orderByMonth();

    @Update("update article set title=#{title}, date=now() where id=#{id}")
    void updateTitle(@Param("id") Integer id, @Param("title") String title);

    @Update("update article set detail=#{detail}, date=now() where id=#{id}")
    void updateDetail(@Param("id") Integer id, @Param("detail") String detail);

    @Update("update article set labelId=#{labelId} where id=#{id}")
    void updateLabel(@Param("id") Integer id, @Param("labelId") Integer labelId);

    @Update("update article set status=#{status} where id=#{id}")
    Integer updateStatus(@Param("id")Integer id, @Param("status")String status);

    @Update("update article set status=#{article.getStatus()}, title = #{article.getTitle()}," +
            " detail = #{article.getContent()}, labelId = #{article.getLabelId()}")
    Integer updateArticle(@Param("article") Article article);

    @Insert("insert into article(date,labelId,detail,title) values(" +
            "#{date},#{label},#{detail},#{title})")
    Integer insertArticle(@Param("date") Date date,
                          @Param("label") Integer label,
                          @Param("detail") String detail,
                          @Param("title") String title);

    @Delete("update article set status='deleted' where id=#{id}")
    Integer deleteById(@Param("id") Integer id);

    class ArticleSqlBuilder {
        public static String buildTotal(Map<String, Object> params) {
            return new SQL() {{
                SELECT("count(id)");
                FROM("article");
                List<String> wheres = new ArrayList<>();
                if (params.get("labelId") != null) {
                    wheres.add("labelId=" + (int)params.get("labelId"));
                }
                if (params.get("month") != null) {
                    wheres.add("date > str_to_date('"+params.get("date1")+
                            "', '%Y-%m-%d') AND date < str_to_date('"+params.get("date2")+
                            "', '%Y-%m-%d')");
                }
                if (params.get("status") != null) {
                    wheres.add("status='" + params.get("status")+"'");
                }
                if (!wheres.isEmpty()) {
                    WHERE(String.join(" AND ", wheres));
                }
                ORDER_BY("date desc");
            }}.toString();
        }

        public static String buildPage(int page, int limit, Map<String, Object> params) {
            return new SQL() {{
                SELECT("id, date, labelId, title, detail as content, status");
                FROM("article");
                List<String> wheres = new ArrayList<>();
                if (params.get("labelId") != null) {
                    wheres.add("labelId=" + (int)params.get("labelId"));
                }
                if (params.get("month") != null) {
                    wheres.add("date > str_to_date('"+params.get("date1")+
                            "', '%Y-%m-%d') AND date < str_to_date('"+params.get("date2")+
                            "', '%Y-%m-%d')");
                }
                if (params.get("status") != null) {
                    wheres.add("status='" + params.get("status") +"'");
                }
                if (!wheres.isEmpty()) {
                    WHERE(String.join(" AND ", wheres));
                }
                ORDER_BY("date desc");
            }}.toString() + " LIMIT #{page}, #{limit}";
        }
    }
}
