package com.aagu.blog.Dao;

import com.aagu.blog.Models.Label;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
@CacheNamespace
public interface LabelDao {

    @Select("select * from label")
    List<Label> getAll();

    @Select("select * from label where parentId = #{parentId}")
    List<Label> getByParentId(@Param("parentId") Integer parentId);

    @Select("select * from label where id not in (select parentId from label)")
    List<Label> getChildLabel();

    @Select("select * from label where id=#{id}")
    Label getById(@Param("id") Integer id);

    @Select("select id from label where name=#{name}")
    Integer getIdByName(@Param("name") String name);

    @Insert("insert into label(parentId, name) value(#{parentId},#{name})")
    Integer insertLabel(@Param("parentId") Integer parentId, @Param("name") String name);

    @Update("update label set parentId=#{pId} where id=#{id}")
    Integer updateParentId(@Param("pId") Integer pId, @Param("id") Integer id);

    @Update("update label set name=#{name} where id=#{id}")
    Integer updateName(@Param("name") String name, @Param("id") Integer id);

    @Delete("delete from label where id=#{id} or parentId=#{id}")
    Integer deleteLabelAndChild(@Param("id") Integer id);
}
