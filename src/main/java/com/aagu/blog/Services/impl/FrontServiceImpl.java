package com.aagu.blog.Services.impl;

import com.aagu.blog.Dao.ArticleDao;
import com.aagu.blog.Models.Article;
import com.aagu.blog.Services.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FrontServiceImpl implements FrontService {
    @Autowired
    ArticleDao articleDao;

    @Override
    public List<Article> getArticleByPage(Integer start, Integer end) {
        return articleDao.getByPage(start, end);
    }
}
