package com.aagu.blog.util;

import com.aagu.blog.dao.BaseDao;
import com.aagu.blog.model.PageModel;

import java.util.Map;

public class Pager<T> {

    private PagerCallback<T> callback;
    private BaseDao<T> dao;

    public Pager(PagerCallback<T> callback) {
        this.callback = callback;
    }

    public Pager(BaseDao<T> dao) {
        this.dao = dao;
    }

    public PageModel<T> getPageByCallback(int page, int num) {
        PageModel<T> data = new PageModel<>();
        data.setTotal(callback.getTotal());
        data.setItems(callback.getItems(page, num));
        data.setCurrent(page);
        return data;
    }

    public PageModel<T> getPage(int page, int num, Map<String, String> params) {
        PageModel<T> data = new PageModel<>();
        data.setTotal(dao.getTotal(params));
        params.put("start", String.valueOf((page - 1) * num));
        params.put("num", String.valueOf(num));
        data.setItems(dao.getItems(params));
        data.setCurrent(page);
        return data;
    }
}