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

    @Insert("insert into label(parentId, name) value(-1,#{name})")
    @SelectKey(statement = "select @@identity", keyProperty = "id", resultType = Integer.class, before = false)
    Integer insertLabel(Label label);

    @Update("update label set parentId=#{pId} where id=#{id}")
    Integer updateParentId(@Param("pId") Integer pId, @Param("id") Integer id);

    @Update("update label set name=#{name} where id=#{id}")
    Integer updateLabel(Label label);

    @Delete("delete from label where id=#{id}")
    Integer deleteLabel(@Param("id") Integer id);
}
