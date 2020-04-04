package com.aagu.blog.service;

import com.aagu.blog.Models.Article;
import com.aagu.blog.Common.ServerResponse;
import com.aagu.blog.Views.ArticleDetailVO;
import com.aagu.blog.Views.BlogVO;

import java.util.Map;

public interface FrontService {

    /**
     * 按ID获取指定文章
     * @param id 文章ID
     * @return Article
     */
    Article getArticleById(Integer id);

    ArticleDetailVO getArticleDetail(Integer id);

    BlogVO getArticlesPage(int page, Map<String, Object> params);

    ServerResponse createComment(String email, String detail, Integer articleId);

    Object getArchiveLabels();

    Object getAllFinalLabels();

}
