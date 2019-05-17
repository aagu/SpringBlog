package com.aagu.blog.Services;

import com.aagu.blog.Models.Article;

import java.util.List;

public interface FrontService {

    List<Article> getArticleByPage(Integer start, Integer end);
}
