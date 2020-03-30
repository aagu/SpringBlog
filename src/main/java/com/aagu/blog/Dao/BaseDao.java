package com.aagu.blog.Dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BaseDao<T> {
    int getTotal(Map<String, Object> params);
    List<T> getItems(@Param("page") int page, @Param("limit") int limit, Map<String, Object> params);
}
