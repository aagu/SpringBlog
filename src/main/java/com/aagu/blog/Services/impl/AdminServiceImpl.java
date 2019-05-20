package com.aagu.blog.Services.impl;

import com.aagu.blog.Dao.ArticleDao;
import com.aagu.blog.Dao.CommentDao;
import com.aagu.blog.Dao.LabelDao;
import com.aagu.blog.Models.Article;
import com.aagu.blog.Models.Comment;
import com.aagu.blog.Models.Label;
import com.aagu.blog.ServerResponse;
import com.aagu.blog.Utils.TextUtil;
import com.aagu.blog.Views.AdminVO;
import com.aagu.blog.Services.AdminService;
import com.aagu.blog.Views.CommentVO;
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
        List<CommentVO> comments = commentDao.getUnread();
        List<Article> articles = getAllArticles();
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
        List<Article> articles = articleDao.getAll();
        for (Article article : articles) {
            article.setDetail(TextUtil.extractTextFromHtml(article.getDetail(), 20));
        }
        return articles;
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

    @Override
    public List<CommentVO> getAllComments() {
        List<CommentVO> comments = commentDao.getAllComment();
        return comments;
    }

    @Override
    public ServerResponse deleteArticle(Integer id) {
        return deleteInfo(id, "article");
    }

    @Override
    public ServerResponse deleteComment(Integer id) {
        return deleteInfo(id, "comment");
    }

    @Override
    public ServerResponse markCommentAsRead(Integer id) {
        if (id != null) {
            commentDao.markAsRead(id);
            return ServerResponse.createBySuccessMessage("OK");
        }
        return ServerResponse.createErrorMessage("failed");
    }

    private ServerResponse deleteInfo(Integer id, String type) {
        if (id != null) {
            int row = -1;
            switch (type) {
                case "article":
                    row = commentDao.deleteById(id);
                    break;
                case "comment":
                    row = articleDao.deleteById(id);
                    break;
                default:
                    break;
            }
            if (row < 0) {
                return ServerResponse.createErrorMessage("fail to delete id:" + id);
            }
            return ServerResponse.createBySuccessMessage("success");
        }
        return ServerResponse.createErrorMessage("invalid parameter");
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
