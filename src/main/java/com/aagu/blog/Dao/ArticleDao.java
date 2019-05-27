package com.aagu.blog.Dao;

import com.aagu.blog.Models.Article;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
@CacheNamespace
public interface ArticleDao {

    @Select("select id, date_format(date, '%Y-%c-%d %H:%i') as date, labelId, title, detail" +
            " from article")
    List<Article> getAll();

    @Select("select id, date_format(date, '%Y-%c-%d %H:%i') as date, labelId, title, detail" +
            " from article where id=#{id}")
    Article getById(@Param("id") Integer id);

    @Select("select id, date_format(date, '%Y-%c-%d %H:%i') as date, labelId, title, detail" +
            " from article" +
            " where labelId=#{labelId}" +
            " order by date desc" +
            " limit #{start}, #{num}")
    List<Article> getByLabel(@Param("labelId") Integer labelId,
                             @Param("start") Integer start,
                             @Param("num") Integer num);

    @Select("select id, date_format(date, '%Y-%c-%d %H:%i') as date, labelId, title, detail" +
            " from article order by date desc" +
            " limit #{start}, #{num}")
    List<Article> getByPage(@Param("start") Integer start, @Param("num") Integer num);

    @Select("select id, date_format(date, '%Y-%c-%d %H:%i') as date, labelId, title, detail" +
            " from article where title like #{key}" +
            " order by date desc" +
            " limit #{start}, #{num}")
    List<Article> getBySearch(@Param("start") Integer start, @Param("num") Integer num, @Param("key") String key);

    @Select("select ceil(count(id)/#{div}) from article")
    Integer getPageCount(@Param("div") Integer div);

    @Select("select max(id) from article where id<#{thisId}")
    Integer getPrevPage(@Param("thisId") Integer id);

    @Select("select min(id) from article where id>#{thisId}")
    Integer getNextPage(@Param("thisId") Integer id);

    @Select("select distinct date_format(date, '%Y %M') from article limit 5")
    List<String> orderByMonth();

    @Update("update article set title=#{title}, date=now() where id=#{id}")
    void updateTitle(@Param("id") Integer id, @Param("title") String title);

    @Update("update article set detail=#{detail}, date=now() where id=#{id}")
    void updateDetail(@Param("id") Integer id, @Param("detail") String detail);

    @Update("update article set labelId=#{labelId} where id=#{id}")
    void updateLabel(@Param("id") Integer id, @Param("labelId") Integer labelId);

    @Insert("insert into article(date,labelId,detail,title) values(" +
            "#{date},#{label},#{detail},#{title})")
    Integer insertArticle(@Param("date") String date,
                          @Param("label") Integer label,
                          @Param("detail") String detail,
                          @Param("title") String title);

    @Delete("delete from article where id=#{id}")
    Integer deleteById(@Param("id") Integer id);
}
