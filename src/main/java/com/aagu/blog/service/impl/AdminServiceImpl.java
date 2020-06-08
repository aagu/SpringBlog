package com.aagu.blog.service.impl;

import com.aagu.blog.dao.ArticleDao;
import com.aagu.blog.dao.CommentDao;
import com.aagu.blog.dao.LabelDao;
import com.aagu.blog.dao.UserDao;
import com.aagu.blog.model.Article;
import com.aagu.blog.model.Comment;
import com.aagu.blog.model.Label;
import com.aagu.blog.model.User;
import com.aagu.blog.common.ServerResponse;
import com.aagu.blog.service.AdminService;
import com.aagu.blog.util.RequestHolder;
import com.aagu.blog.util.TextUtil;
import com.aagu.blog.view.TagTree;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.aagu.blog.common.Const.ARTICLE_ADMIN_PAGE_LEN;
import static com.aagu.blog.common.Const.COMMENT_PAGE_LEN;

@Service
public class AdminServiceImpl implements AdminService {

    private final ArticleDao articleDao;

    private final CommentDao commentDao;

    private final LabelDao labelDao;

    private final UserDao userDao;

    private final StringRedisTemplate redisTemplate;

    public AdminServiceImpl(ArticleDao articleDao, CommentDao commentDao, LabelDao labelDao,
                            UserDao userDao, StringRedisTemplate redisTemplate) {
        this.articleDao = articleDao;
        this.commentDao = commentDao;
        this.labelDao = labelDao;
        this.userDao = userDao;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<Label> getAllFinalLabels() {
        return labelDao.getChildLabel();
    }

    @Override
    public Integer getArticleCount() {
        return articleDao.getPageCount(1);
    }

    @Override
    public List<Article> getArticleByLabel(Integer labelId, Integer page) {
        List<Article> articles = articleDao.getByLabel(labelId, (page-1)*ARTICLE_ADMIN_PAGE_LEN, ARTICLE_ADMIN_PAGE_LEN);
        for (Article article : articles) {
            article.setContent(TextUtil.extractTextFromHtml(article.getContent(), 20));
        }
        return articles;
    }

    @Override
    public List<Article> getArticleBySearch(String key, Integer page) {
        List<Article> articles = articleDao.getBySearch((page-1)*ARTICLE_ADMIN_PAGE_LEN, ARTICLE_ADMIN_PAGE_LEN, "%"+key+"%");
        for (Article article : articles) {
            article.setContent(TextUtil.extractTextFromHtml(article.getContent(), 20));
        }
        return articles;
    }

    @Override
    public Map<String, Object> getTreeViewData() {
        TagTree rootVO = new TagTree(-1, -1, "root");
        Label root = new Label(-1, -1, "root");

        int maxId = LevelTraverse(root, rootVO, root.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("maxId", maxId);
        map.put("tree", rootVO);
        return map;
    }

    @Override
    public List<Comment> getCommentByPage(Integer page, String search, String order, String status) {
        return commentDao.getByPage((page-1)*COMMENT_PAGE_LEN, COMMENT_PAGE_LEN, search, order, status);
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
    public String login(String name, String pwd) {
        if (TextUtil.notEmpty(name) && TextUtil.notEmpty(pwd)) {
            if (pwd.equals(userDao.getPassword(name))) {
                String sessionId = RequestHolder.Companion.getSession().getId();
                redisTemplate.opsForValue().set(sessionId, name, 2, TimeUnit.HOURS);
                return sessionId;
            }
        }
        return "error";
    }

    @Override
    public ServerResponse<String> logout() { ;
        String sessionId = RequestHolder.Companion.getSession().getId();
        redisTemplate.delete(sessionId);
        return ServerResponse.createBySuccessMessage("退出登录");
    }

    @Override
    public Integer getCommentPages() {
        return commentDao.getCount(COMMENT_PAGE_LEN);
    }

    @Override
    public Integer getArticlePages(Integer labelId, String search) {
        if (labelId != null) return articleDao.getByLabelCount(labelId, 5);
        if (search != null) return articleDao.getBySearchCount(search, 5);
        return articleDao.getPageCount(5);
    }

    @Override
    public Map<String, Object> updateParentLabel(Integer parentId, Integer id) {
        if (parentId < 1) return null;
        labelDao.updateParentId(parentId, id);
        return getTreeViewData();
    }

    @Override
    public List<User> getUserByPage(Integer page) {
        return userDao.getByPage((page-1)*4, 4);
    }

    @Override
    public Map<String, String> getUserInfo(String token) {
        String name = redisTemplate.opsForValue().get(token);

        if (name == null) return null;

        User user = userDao.getByName(name);
        if (user != null) {
            Map<String, String> params = new HashMap<>();
            params.put("name", name);
            params.put("roles", user.getRole());
            params.put("introduction", user.getName());
            params.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            params.put("email", user.getEmail());
            return params;
        }
        return null;
    }

    @Override
    public List<String> getAllUserByName(String name) {
        if (name == null) name = "";
        return userDao.getAllNameLike("%"+name+"%");
    }

    private ServerResponse deleteInfo(Integer id, String type) {
        if (id != null) {
            int row = -1;
            switch (type) {
                case "article":
                    row = articleDao.deleteById(id);
                    break;
                case "comment":
                    row = commentDao.deleteById(id);
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

    private int LevelTraverse(Label root, TagTree rootVO, Integer maxId) {
        int max = maxId;
        List<Label> data = labelDao.getByParentId(root.getId());
        Set<TagTree> child = new HashSet<>();
        for (Label label : data) {
            if (label.getId() > max) max = label.getId();
            TagTree vo = new TagTree(label.getId(), label.getParentId(), label.getName());
            vo.setParentName(root.getName());
            max = LevelTraverse(label, vo, max);
            child.add(vo);
            rootVO.setChildren(child);
        }
        return max;
    }
}
