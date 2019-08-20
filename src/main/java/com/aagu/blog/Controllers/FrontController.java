package com.aagu.blog.Controllers;

import com.aagu.blog.Common.ServerResponse;
import com.aagu.blog.Services.FrontService;
import com.aagu.blog.Views.ArticleDetailVO;
import com.aagu.blog.Views.BlogVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;

@Controller
public class FrontController {

    private final FrontService frontService;

    private static final String DATA = "data";
    private static final String DEFAULT_RESULT = "defaultData";
    public static final String KEY_READ_RECORD = "readCount";

    public FrontController(FrontService frontService) {
        this.frontService = frontService;
    }

    /**
     * 主页
     * @return 重定向到博客页
     */
    @GetMapping(value = "")
    public String main() {
        return "redirect:/blog";
    }

    /**
     * 博客页
     * @param label 分类标签
     * @param page 分页
     * @param keyWord 关键词
     * @param model ThymeLeaf Model
     * @return 指定分页的博客页
     */
    @GetMapping(value = "/blog")
    public String blog(@RequestParam(value = "label", required = false) String label,
                @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                @RequestParam(value = "search", required = false) String keyWord,
                Model model, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        session.getAttribute("count");
        BlogVO blogVO;
        if (label != null && !label.isEmpty()) {
            blogVO = frontService.getPageByLabel(label, page);
        } else if (keyWord != null && !keyWord.isEmpty()){
            blogVO = frontService.getSearchedPage(keyWord, page);
        } else {
            blogVO = frontService.getMainPage(page);
        }
        model.addAttribute(DATA, blogVO);
        return "front/blog";
    }

    @GetMapping(value = "/about")
    public String about() {
        return "front/about";
    }

    /**
     * 返回index指定的详情页
     * @param index 文章编号
     * @param model ThymeLeaf Model
     * @return 详情页地址
     */
    @GetMapping(value = "/detail/{index}")
    public String detail(@PathVariable(value = "index", required = false) Integer index, Model model) {
        if (index == null) {
            model.addAttribute(DEFAULT_RESULT, "不记得你要哪篇内容了");
            return "error";
        }
        ServerResponse<ArticleDetailVO> article = frontService.getArticleDetail(index);
        if (!article.isSuccess()) {
            model.addAttribute(DEFAULT_RESULT, "找不到文章");
            return "error";
        }
        model.addAttribute(DATA, article);
        return "front/detail";
    }

    /**
     * 评论文章
     * @param email 评论者邮箱
     * @param detail 评论详情
     * @param articleId 文章编号
     * @return 当前文章详情页
     */
    @PostMapping(value = "/put-comment")
    public String createComment(@Email String email, String detail, Integer articleId) {
        frontService.createComment(email, detail, articleId);
        return "redirect:/detail/" + articleId;
    }

}
