package com.aagu.blog.service.impl;

import com.aagu.blog.Common.ServerResponse;
import com.aagu.blog.Dao.ArticleDao;
import com.aagu.blog.Dao.CommentDao;
import com.aagu.blog.Dao.LabelDao;
import com.aagu.blog.Models.Article;
import com.aagu.blog.Models.Label;
import com.aagu.blog.Models.PageModel;
import com.aagu.blog.Utils.Pager;
import com.aagu.blog.Utils.TextUtil;
import com.aagu.blog.Views.ArchiveLabel;
import com.aagu.blog.Views.BlogVO;
import com.aagu.blog.service.FrontService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static com.aagu.blog.Common.Const.ARTICLE_PAGE_LEN;

@Service
public class FrontServiceImpl implements FrontService {
    private final ArticleDao articleDao;

    private final LabelDao labelDao;

    private final CommentDao commentDao;

    public FrontServiceImpl(ArticleDao articleDao, LabelDao labelDao, CommentDao commentDao) {
        this.articleDao = articleDao;
        this.labelDao = labelDao;
        this.commentDao = commentDao;
    }

//    @Override
//    public Article getArticleById(Integer id) {
//        if (id != null) {
//            Article article = articleDao.getById(id);
//            return article;
//        }
//        return null;
//    }

//    @Override
//    public ArticleDetailVO getArticleDetail(Integer id) {
//        ArticleDetailVO vo = new ArticleDetailVO();
//        Article article = getArticleById(id);
//        if (article != null) {
//            Label label = labelDao.getById(article.getLabelId());
//            vo.setArticle(article);
//            vo.setLabel(label);
//            vo.setAllLabels(labelDao.getChildLabel());
//            Integer prev = articleDao.getPrevPage(id);
//            Integer next = articleDao.getNextPage(id);
//            if (prev == null) {
//                prev = 0;
//            }
//            vo.setPrev(prev);
//            if (next == null) {
//                // 每页一篇文章，即获取文章总数
//                next = articleDao.getPageCount(1);
//            }
//            vo.setNext(next);
//            return vo;
//        }
//        return null;
//    }

    @Override
    public BlogVO getArticlesPage(int page, Map<String, Object> params) {
        BlogVO blogVO = new BlogVO();
        if (params.get("month") != null) {
            LocalDate date = ((Date)params.get("month")).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            params.put("date1", date);
            params.put("date2", date.plusMonths(1));
        }
        if (params.get("labelId") != null) {
            blogVO.setCurreLabel(labelDao.getById((int)params.get("labelId")).getName());
        }
        PageModel<Article> articles = new Pager<>(articleDao).getPage(page, ARTICLE_PAGE_LEN, params);
        setPage(blogVO, articles, page);
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

    @Override
    public Object getArchiveLabels() {
        List<String> labels = articleDao.orderByMonth();
        Map<String, Object> resp = new HashMap<>();
        resp.put("group", "archive");
        resp.put("text", "归档");
        resp.put("icon", "archive");
        List<ArchiveLabel> set = new ArrayList<>();
        for (String l: labels) {
            set.add(new ArchiveLabel(TextUtil.getZhMonth(l), TextUtil.getDigitMonth(l)));
        }
        resp.put("children", set);
        return resp;
    }

    @Override
    public Object getAllFinalLabels() {
        List<Label> labels = labelDao.getChildLabel();
        Map<String, Object> resp = new HashMap<>();
        resp.put("group", "category");
        resp.put("text", "标签");
        resp.put("icon", "label");
        Set<ArchiveLabel> set = new HashSet<>();
        for (Label l: labels) {
            set.add(new ArchiveLabel(l.getName(), String.valueOf(l.getId())));
        }
        resp.put("children", set);
        return resp;
    }

    private void setPage(BlogVO blogVO, PageModel<Article> articles, Integer page) {
        articles.getItems().forEach(article -> {
            article.setUrl("/detail/" + article.getId());
            article.setContent(TextUtil.extractTextFromHtml(article.getContent(), 40));
        });
        blogVO.setArticles(articles.getItems());
        blogVO.setPages((int) Math.ceil((float)articles.getTotal()/ARTICLE_PAGE_LEN));
        blogVO.setLabels(labelDao.getChildLabel());
        blogVO.setCurrePage(page);
        blogVO.setArchiveLabel(articleDao.orderByMonth());
        blogVO.setParam("");
    }

}
