package com.aagu.blog.Services;

import com.aagu.blog.Models.Article;
import com.aagu.blog.Models.Comment;
import com.aagu.blog.Models.Label;
import com.aagu.blog.Common.ServerResponse;
import com.aagu.blog.Views.AdminVO;
import com.aagu.blog.Views.LabelManageVO;

import java.util.List;
import java.util.Set;

public interface AdminService {

    AdminVO getMainAdminPage(Integer page);

    List<Label> getAllFinalLabels();

    List<Article> getArticleByPage(Integer page);

    List<Article> getArticleByLabel(Integer labelId, Integer page);

    List<Article> getArticleBySearch(String key, Integer page);

    List<Label> getAllLabels();

    ServerResponse<Set<LabelManageVO>> getTreeViewData();

    ServerResponse<Article> updateArticle(Article article);

    ServerResponse<Article> publishArticle(Article article);

    List<Comment> getCommentByPage(Integer page, String search, String order);

    ServerResponse deleteArticle(Integer id);

    ServerResponse deleteComment(Integer id);

    ServerResponse markCommentAsRead(Integer id);

    ServerResponse login(String name, String pwd);

    ServerResponse logout();

    Integer getCommentPages();

    Integer getArticlePages(Integer labelId, String search);

    ServerResponse addLabel(String tag, Integer parentId);

    ServerResponse updateParentLabel(Integer parentId, Integer id);

    ServerResponse updateLabelName(String name, Integer id);

    ServerResponse deleteLabel(Integer id);

}
