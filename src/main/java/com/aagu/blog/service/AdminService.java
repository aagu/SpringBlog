package com.aagu.blog.service;

import com.aagu.blog.Models.Article;
import com.aagu.blog.Models.Comment;
import com.aagu.blog.Models.Label;
import com.aagu.blog.Models.User;
import com.aagu.blog.common.ServerResponse;

import java.util.List;
import java.util.Map;

public interface AdminService {

    List<Label> getAllFinalLabels();

    Integer getArticleCount();

    List<Article> getArticleByLabel(Integer labelId, Integer page);

    List<Article> getArticleBySearch(String key, Integer page);

    Map<String, Object> getTreeViewData();

    List<Comment> getCommentByPage(Integer page, String search, String order, String status);

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
