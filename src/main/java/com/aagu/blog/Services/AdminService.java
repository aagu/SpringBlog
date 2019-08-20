package com.aagu.blog.Services;

import com.aagu.blog.Models.*;
import com.aagu.blog.Common.ServerResponse;
import com.aagu.blog.Views.AdminVO;
import com.aagu.blog.Views.TagTree;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    List<Comment> getCommentByPage(Integer page, String search, String order);

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

    Map getUserInfo(String name);

    List<String> getAllUserByName(String name);
}
