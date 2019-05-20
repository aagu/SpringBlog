package com.aagu.blog.Services;

import com.aagu.blog.Models.Article;
import com.aagu.blog.ServerResponse;
import com.aagu.blog.Views.BlogVO;

import java.util.List;

public interface FrontService {

    List<Article> getArticleByPage(Integer start, Integer end);

    ServerResponse<Article> getArticleById(Integer id);

    BlogVO getMainPage();
}
