package com.aagu.blog.dao;

import com.aagu.blog.model.Resource;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ResourceDao {
    @Select("select id, type, url, `desc`, status, date_format(createTime, '%Y-%c-%d %H:%i') as createTime" +
            " from file")
    List<Resource> getAll();

    @Select("select id, type, url, `desc`, status, date_format(createTime, '%Y-%c-%d %H:%i') as createTime" +
            " from file limit #{start}, #{num}")
    List<Resource> getByPage(@Param("start")Integer start, @Param("num")Integer num);

    @Select("select id, type, url, `desc`, status, date_format(createTime, '%Y-%c-%d %H:%i') as createTime" +
            " from file where id=#{id}")
    Resource getById(@Param("id")Integer id);

    @Select("select id, type, url, `desc`, status, date_format(createTime, '%Y-%c-%d %H:%i') as createTime" +
            " from file where type=#{type}")
    List<Resource> getByType(@Param("type")String type);

    @Insert("insert into file(type,url,`desc`) value(#{type},#{url},#{desc})")
    Integer insert(@Param("type")String type, @Param("url")String url, @Param("desc")String desc);

    @Update("update file set url=#{url}, `desc`=#{desc}, createTime=now() where id=#{id}")
    Integer update(@Param("id")Integer id, @Param("url")String url, @Param("desc")String desc);

    @Update("update file set status=#{status} where id=#{id}")
    Integer updateStatus(@Param("id")Integer id, @Param("status")String status);

    @Delete("delete from file where id=#{id}")
    Integer delete(@Param("id")Integer id);
}
