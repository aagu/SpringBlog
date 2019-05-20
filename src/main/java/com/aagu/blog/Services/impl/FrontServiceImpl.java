package com.aagu.blog.Services.impl;

import com.aagu.blog.Dao.ArticleDao;
import com.aagu.blog.Dao.LabelDao;
import com.aagu.blog.Models.Article;
import com.aagu.blog.ServerResponse;
import com.aagu.blog.Services.FrontService;
import com.aagu.blog.Views.BlogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FrontServiceImpl implements FrontService {
    @Autowired
    ArticleDao articleDao;

    @Autowired
    LabelDao labelDao;

    @Override
    public List<Article> getArticleByPage(Integer start, Integer end) {
        return articleDao.getByPage(start, end);
    }

    @Override
    public ServerResponse<Article> getArticleById(Integer id) {
        if (id != null) {
            Article article = articleDao.getById(id);
            if (article == null) {
                return ServerResponse.createErrorMessage("article not found");
            }
            return ServerResponse.createBySuccess(article);
        }
        return ServerResponse.createErrorMessage("wrong parameter");
    }

    @Override
    public BlogVO getMainPage() {
        BlogVO blogVO = new BlogVO();
        blogVO.setArticles(articleDao.getByPage(0, 5));
        blogVO.setLabels(labelDao.getChildLabel());
        return blogVO;
    }
}
