package com.aagu.blog.Dao;

import com.aagu.blog.Models.Label;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LabelDao {

    @Select("select * from label")
    List<Label> getAll();

    @Select("select * from label where parentId = #{parentId}")
    List<Label> getByParentId(@Param("parentId") Integer parentId);

    @Select("select * from label where id not in (select parentId from label)")
    List<Label> getChildLabel();
}
