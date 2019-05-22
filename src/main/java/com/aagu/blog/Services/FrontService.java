package com.aagu.blog.Services;

import com.aagu.blog.Models.Article;
import com.aagu.blog.ServerResponse;
import com.aagu.blog.Views.ArticleDetailVO;
import com.aagu.blog.Views.BlogVO;

import java.util.List;

public interface FrontService {

    List<Article> getArticleByPage(Integer start, Integer end);

    ServerResponse<Article> getArticleById(Integer id);

    ServerResponse<ArticleDetailVO> getArticleDetail(Integer id);

    BlogVO getMainPage(Integer start, Integer end);

    ServerResponse createComment(String email, String detail, Integer articleId);
}
