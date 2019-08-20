package com.aagu.blog.Utils;

import java.util.List;

public interface PagerCallback<T> {
    int getTotal();

    List<T> getItems(int page, int limit);
}