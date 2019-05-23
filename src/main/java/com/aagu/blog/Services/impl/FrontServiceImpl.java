package com.aagu.blog.Services.impl;

import com.aagu.blog.Dao.ArticleDao;
import com.aagu.blog.Dao.CommentDao;
import com.aagu.blog.Dao.LabelDao;
import com.aagu.blog.Models.Article;
import com.aagu.blog.Models.Comment;
import com.aagu.blog.Models.Label;
import com.aagu.blog.Common.ServerResponse;
import com.aagu.blog.Services.FrontService;
import com.aagu.blog.Utils.TextUtil;
import com.aagu.blog.Views.ArticleDetailVO;
import com.aagu.blog.Views.BlogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.aagu.blog.Common.Const.*;

@Service
public class FrontServiceImpl implements FrontService {
    @Autowired
    ArticleDao articleDao;

    @Autowired
    LabelDao labelDao;

    @Autowired
    CommentDao commentDao;

    @Override
    public List<Article> getArticleByPage(Integer start, Integer end) {
        List<Article> articles = articleDao.getByPage(start, end);
        for (Article article : articles) {
            article.setDetail(TextUtil.extractTextFromHtml(article.getDetail(), 15));
        }
        return articles;
    }

    @Override
    public ServerResponse<Article> getArticleById(Integer id) {
        if (id != null) {
            Article article = articleDao.getById(id);
            if (article == null) {
                return ServerResponse.createErrorMessage("article not found");
            }
            return ServerResponse.createBySuccess(article);
        }
        return ServerResponse.createErrorMessage("wrong parameter");
    }

    @Override
    public ServerResponse<ArticleDetailVO> getArticleDetail(Integer id) {
        ArticleDetailVO vo = new ArticleDetailVO();
        ServerResponse<Article> articleResponse = getArticleById(id);
        if (articleResponse.isSuccess()) {
            Article article = articleResponse.getData();
            Label label = labelDao.getById(article.getLabelId());
            List<Comment> comments = commentDao.getByArticle(article.getId());
            vo.setArticle(article);
            vo.setLabel(label);
            vo.setComments(comments);
            vo.setCommentCount(comments.size());
            return ServerResponse.createBySuccess(vo);
        }
        return ServerResponse.createBySuccessMessage("article not found");
    }

    @Override
    public BlogVO getMainPage(Integer page) {
        BlogVO blogVO = new BlogVO();
        List<Article> articles = articleDao.getByPage((page-1) * ARTICLE_PAGE_LEN, page * ARTICLE_PAGE_LEN - 1);
        for (Article article : articles) {
            article.setDetail(TextUtil.extractTextFromHtml(article.getDetail(), 15));
        }
        blogVO.setArticles(articles);
        blogVO.setLabels(labelDao.getChildLabel());
        blogVO.setPages(articleDao.getPageCount(ARTICLE_PAGE_LEN));
        blogVO.setCurrePage(page);
        return blogVO;
    }

    @Override
    public ServerResponse createComment(String email, String detail, Integer articleId) {
        int res = commentDao.insertComment(detail, email, articleId);
        if (res < 0) {
            return ServerResponse.createErrorMessage("评论插入失败");
        }
        return ServerResponse.createBySuccessMessage("ok");
    }
}
