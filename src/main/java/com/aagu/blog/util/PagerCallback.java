package com.aagu.blog.util;

import java.util.List;

public interface PagerCallback<T> {
    int getTotal();

    List<T> getItems(int page, int limit);
}