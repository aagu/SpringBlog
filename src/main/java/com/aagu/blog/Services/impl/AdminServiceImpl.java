package com.aagu.blog.Services.impl;

import com.aagu.blog.Dao.ArticleDao;
import com.aagu.blog.Dao.CommentDao;
import com.aagu.blog.Dao.LabelDao;
import com.aagu.blog.Models.Article;
import com.aagu.blog.Models.Comment;
import com.aagu.blog.Models.Label;
import com.aagu.blog.ServerResponse;
import com.aagu.blog.Views.AdminVO;
import com.aagu.blog.Services.AdminService;
import com.aagu.blog.Views.LabelManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public ServerResponse<Set<LabelManageVO>> getTreeViewData() {
        Set<LabelManageVO> data = new HashSet<>();
        LabelManageVO rootVO = new LabelManageVO();
        Label root = new Label(-1, -1, "root");

        LevelTraverse(data, root, rootVO);
        return ServerResponse.createBySuccess(data);
    }

    @Override
    public ServerResponse<Article> updateArticle(Article article) {
        int id = article.getId();
        Article oldArticle = articleDao.getById(id);
        if (oldArticle != null) {
            if (!oldArticle.getDetail().equals(article.getDetail())) {
                articleDao.updateDetail(id, article.getDetail());
            }
            if (!oldArticle.getLabelId().equals(article.getLabelId())) {
                articleDao.updateLabel(id, article.getLabelId());
            }
            if (!oldArticle.getTitle().equals(article.getTitle())) {
                articleDao.updateTitle(id, article.getTitle());
            }
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse<Article> publishArticle(Article article) {
        articleDao.insertArticle(article.getDate(), article.getLabelId(), article.getDetail(), article.getTitle());
        return ServerResponse.createBySuccess();
    }

    private void LevelTraverse(Set<LabelManageVO> child, Label root, LabelManageVO rootVO) {
        List<Label> data = labelDao.getByParentId(root.getId());
        rootVO.setTags(Collections.singletonList(data.size() + ""));
        for (Label label : data) {
            LabelManageVO vo = new LabelManageVO();
            vo.setText(label.getName());
            vo.setId(label.getId());
            vo.setParentId(root.getId());
            vo.setParentName(root.getName());
            Set<LabelManageVO> Lchild = new HashSet<>();
            LevelTraverse(Lchild, label, vo);
            child.add(vo);
            rootVO.setNodes(child);
        }
    }
}
