package com.aagu.blog.Models;

import java.util.List;

public class PageModel<T> {
    List<T> items;
    int total;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
