package com.aagu.blog.service;

import com.aagu.blog.Common.ServerResponse;
import com.aagu.blog.Views.BlogVO;

import java.util.Map;

public interface FrontService {

//    Article getArticleById(Integer id);

//    ArticleDetailVO getArticleDetail(Integer id);

    BlogVO getArticlesPage(int page, Map<String, Object> params);

    ServerResponse createComment(String email, String detail, Integer articleId);

    Object getArchiveLabels();

    Object getAllFinalLabels();

}
