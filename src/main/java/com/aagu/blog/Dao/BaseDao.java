package com.aagu.blog.Dao;

import java.util.List;

public interface BaseDao<T> {
    int getTotal();
    List<T> getItems(int page, int limit);
}
