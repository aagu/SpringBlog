package com.aagu.blog.Utils;

import com.aagu.blog.Dao.BaseDao;
import com.aagu.blog.Models.PageModel;

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
        return data;
    }

    public PageModel<T> getPage(int page, int num, Map<String, Object> params) {
        PageModel<T> data = new PageModel<>();
        data.setTotal(dao.getTotal(params));
        data.setItems(dao.getItems((page-1)*num, num, params));
        return data;
    }
}