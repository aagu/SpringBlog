package com.aagu.blog.Services.impl;

import com.aagu.blog.Dao.ArticleDao;
import com.aagu.blog.Dao.CommentDao;
import com.aagu.blog.Dao.LabelDao;
import com.aagu.blog.Models.Article;
import com.aagu.blog.Models.Comment;
import com.aagu.blog.Models.Label;
import com.aagu.blog.Views.AdminVO;
import com.aagu.blog.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private LabelDao labelDao;

    @Override
    public AdminVO getMainAdminPage(Integer page) {
        if (page == null) {
            return null;
        }
        AdminVO adminVO = new AdminVO();
        List<Comment> comments = commentDao.getAll();
        List<Article> articles = articleDao.getAll();
        adminVO.setComments(comments);
        adminVO.setArticles(articles);
        adminVO.setArticleCount(articles.size());
        adminVO.setInfoCount(comments.size());
        adminVO.setPeopleCount(1);
        return adminVO;
    }

    @Override
    public List<Label> getAllFinalLabels() {
        return labelDao.getChildLabel();
    }

    @Override
    public List<Article> getAllArticles() {
        return articleDao.getAll();
    }

    @Override
    public List<Label> getAllLabels() {
        return labelDao.getAll();
    }
}
