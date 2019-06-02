package com.aagu.blog.Services.impl;

import com.aagu.blog.Dao.ArticleDao;
import com.aagu.blog.Dao.CommentDao;
import com.aagu.blog.Dao.LabelDao;
import com.aagu.blog.Models.Article;
import com.aagu.blog.Models.Comment;
import com.aagu.blog.Models.Label;
import com.aagu.blog.Common.ServerResponse;
import com.aagu.blog.Utils.TextUtil;
import com.aagu.blog.Views.AdminVO;
import com.aagu.blog.Services.AdminService;
import com.aagu.blog.Views.LabelManageVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.*;

import static com.aagu.blog.Common.Const.*;

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
        List<Comment> comments = commentDao.getUnread();
        List<Article> articles = getArticleByPage(page);
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
    public List<Article> getArticleByPage(Integer page) {
        List<Article> articles = articleDao.getByPage((page-1)*ARTICLE_ADMIN_PAGE_LEN, ARTICLE_ADMIN_PAGE_LEN);
        for (Article article : articles) {
            article.setDetail(TextUtil.extractTextFromHtml(article.getDetail(), 20));
        }
        return articles;
    }

    @Override
    public List<Article> getArticleByLabel(Integer labelId, Integer page) {
        List<Article> articles = articleDao.getByLabel(labelId, (page-1)*ARTICLE_ADMIN_PAGE_LEN, ARTICLE_ADMIN_PAGE_LEN);
        for (Article article : articles) {
            article.setDetail(TextUtil.extractTextFromHtml(article.getDetail(), 20));
        }
        return articles;
    }

    @Override
    public List<Article> getArticleBySearch(String key, Integer page) {
        List<Article> articles = articleDao.getBySearch((page-1)*ARTICLE_ADMIN_PAGE_LEN, ARTICLE_ADMIN_PAGE_LEN, "%"+key+"%");
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
    public List<Comment> getCommentByPage(Integer start, Integer end, String search) {
        return commentDao.getByPage(start, end, search);
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
            int res = commentDao.markAsRead(id);
            if (res >= 0) return ServerResponse.createBySuccessMessage("OK");
        }
        return ServerResponse.createErrorMessage("failed");
    }

    @Override
    public ServerResponse login(String name, String pwd) {
        if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(pwd)) {
            UsernamePasswordToken token = new UsernamePasswordToken(name, pwd);
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.login(token);
                return ServerResponse.createBySuccessMessage("登录成功");
            } catch (AuthenticationException e) {
                return ServerResponse.createErrorMessage("用户名和密码不匹配");
            }
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

    @Override
    public ServerResponse logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ServerResponse.createBySuccessMessage("退出登录");
    }

    @Override
    public Integer getCommentPages() {
        return commentDao.getCommentCount(COMMENT_PAGE_LEN);
    }

    @Override
    public Integer getArticlePages(Integer labelId, String search) {
        if (labelId != null) return articleDao.getByLabelCount(labelId, 5);
        if (search != null) return articleDao.getBySearchCount(search, 5);
        return articleDao.getPageCount(5);
    }

    @Override
    public ServerResponse addLabel(String tag, Integer parentId) {
        Integer res = labelDao.insertLabel(parentId, tag);
        if (res < 0) return ServerResponse.createErrorMessage("failed to insert label");
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse updateParentLabel(Integer parentId, Integer id) {
        if (parentId < 1) return ServerResponse.createErrorMessage("父标签不存在");
        Integer res = labelDao.updateParentId(parentId, id);
        if (res < 0) return ServerResponse.createErrorMessage("修改失败");
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse updateLabelName(String name, Integer id) {
        if (name.isEmpty()) return ServerResponse.createErrorMessage("标签名为空");
        Integer res = labelDao.updateName(name, id);
        if (res < 0) return ServerResponse.createErrorMessage("修改失败");
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse deleteLabel(Integer id) {
        labelDao.deleteLabelAndChild(id);
        return ServerResponse.createBySuccess();
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
