package com.aagu.blog.Services.impl;

import com.aagu.blog.Dao.ArticleDao;
import com.aagu.blog.Dao.LabelDao;
import com.aagu.blog.Models.Article;
import com.aagu.blog.ServerResponse;
import com.aagu.blog.Services.FrontService;
import com.aagu.blog.Utils.TextUtil;
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
        List<Article> articles = articleDao.getByPage(start, end);
        for (Article article : articles) {
            article.setDetail(TextUtil.extractTextFromHtml(article.getDetail(), 15));
        }
        return articles;
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
    public BlogVO getMainPage(Integer start, Integer end) {
        BlogVO blogVO = new BlogVO();
        List<Article> articles = articleDao.getByPage(start, end);
        for (Article article : articles) {
            article.setDetail(TextUtil.extractTextFromHtml(article.getDetail(), 15));
        }
        blogVO.setArticles(articles);
        blogVO.setLabels(labelDao.getChildLabel());
        blogVO.setPages(articleDao.getPageCount(3));
        return blogVO;
    }
}
