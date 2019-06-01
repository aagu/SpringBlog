package com.aagu.blog.Services;

import com.aagu.blog.Models.Article;
import com.aagu.blog.Common.ServerResponse;
import com.aagu.blog.Views.ArticleDetailVO;
import com.aagu.blog.Views.BlogVO;

import java.util.List;

public interface FrontService {

    /**
     * 获取文章列表的分页
     * @param start 起始位置
     * @param num 数目
     * @return List<Article>
     */
    List<Article> getArticleByPage(Integer start, Integer num);

    /**
     * 按ID获取指定文章
     * @param id 文章ID
     * @return Article
     */
    ServerResponse<Article> getArticleById(Integer id);

    ServerResponse<ArticleDetailVO> getArticleDetail(Integer id);

    /**
     * 获取某一页的文章
     * @param page 页数
     * @return BlogVO
     */
    BlogVO getMainPage(Integer page);

    BlogVO getPageByLabel(String label, Integer page);

    BlogVO getSearchedPage(String key, Integer page);

    ServerResponse createComment(String email, String detail, Integer articleId);
}
