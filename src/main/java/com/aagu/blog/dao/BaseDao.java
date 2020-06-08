package com.aagu.blog.dao;

import java.util.List;
import java.util.Map;

public interface BaseDao<T> {
    int getTotal(Map<String, String> params);
    List<T> getItems(Map<String, String> params);
}
