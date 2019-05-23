package com.aagu.blog.Services;

import com.aagu.blog.Models.Article;
import com.aagu.blog.Models.Comment;
import com.aagu.blog.Models.Label;
import com.aagu.blog.Common.ServerResponse;
import com.aagu.blog.Views.AdminVO;
import com.aagu.blog.Views.CommentVO;
import com.aagu.blog.Views.LabelManageVO;

import java.util.List;
import java.util.Set;

public interface AdminService {

    AdminVO getMainAdminPage(Integer page);

    List<Label> getAllFinalLabels();

    List<Article> getAllArticles();

    List<Label> getAllLabels();

    ServerResponse<Set<LabelManageVO>> getTreeViewData();

    ServerResponse<Article> updateArticle(Article article);

    ServerResponse<Article> publishArticle(Article article);

    List<Comment> getCommentByPage(Integer start, Integer end);

    ServerResponse deleteArticle(Integer id);

    ServerResponse deleteComment(Integer id);

    ServerResponse markCommentAsRead(Integer id);

    ServerResponse login(String name, String pwd);

    Integer getCommentPages();

}
