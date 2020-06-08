package com.aagu.blog.service;

import com.aagu.blog.common.ServerResponse;
import com.aagu.blog.view.BlogVO;

import java.util.Map;

public interface FrontService {

//    Article getArticleById(Integer id);

//    ArticleDetailVO getArticleDetail(Integer id);

    BlogVO getArticlesPage(int page, Map<String, String> params);

    ServerResponse createComment(String email, String detail, Integer articleId);

    Object getArchiveLabels();

    Object getAllFinalLabels();

}
