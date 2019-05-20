package com.aagu.blog.Dao;

import com.aagu.blog.Models.Article;
import org.apache.ibatis.annotations.*;

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

    @Select("select * from article limit #{start}, #{end}")
    List<Article> getByPage(@Param("start") Integer start, @Param("end") Integer end);

    @Select("select count(id)/#{div} from article")
    Integer getPageCount(@Param("div") Integer div);

    @Update("update article set title=#{title} date=now() where id=#{id}")
    void updateTitle(@Param("id") Integer id, @Param("title") String title);

    @Update("update article set detail=#{detail} date=now() where id=#{id}")
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
