package com.aagu.blog.service;

import com.aagu.blog.Models.*;
import com.aagu.blog.common.ServerResponse;

import java.util.List;
import java.util.Map;

public interface AdminService {

    List<Label> getAllFinalLabels();

    Integer getArticleCount();

    PageModel<Article> getArticleByPage(Integer page, Integer limit);

    List<Article> getArticleByLabel(Integer labelId, Integer page);

    List<Article> getArticleBySearch(String key, Integer page);

    List<Label> getAllLabels();

    Map<String, Object> getTreeViewData();

    void addArticle(Article article);

    ServerResponse<Article> updateArticle(Article article);

    ServerResponse<Article> publishArticle(Integer id);

    List<Comment> getCommentByPage(Integer page, String search, String order, String status);

    ServerResponse deleteArticle(Integer id);

    ServerResponse deleteComment(Integer id);

    ServerResponse markCommentAsRead(Integer id);

    String login(String name, String pwd);

    ServerResponse logout();

    Integer getCommentPages();

    Integer getArticlePages(Integer labelId, String search);

    Map<String, Object> addLabel(String tag, Integer parentId);

    Map<String, Object> updateParentLabel(Integer parentId, Integer id);

    Map<String, Object> updateLabelName(String name, Integer id);

    Map<String, Object> deleteLabel(String name);

    List<User> getUserByPage(Integer page);

    Map<String, String> getUserInfo(String name);

    List<String> getAllUserByName(String name);
}
