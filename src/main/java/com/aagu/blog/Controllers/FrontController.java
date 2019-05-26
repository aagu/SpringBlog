package com.aagu.blog.Controllers;

import com.aagu.blog.Common.ServerResponse;
import com.aagu.blog.Services.FrontService;
import com.aagu.blog.Views.ArticleDetailVO;
import com.aagu.blog.Views.BlogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;

@Controller
public class FrontController {

    @Autowired
    FrontService frontService;

    private static final String DATA = "data";
    private static final String DEFAULT_RESULT = "defaultData";

    @GetMapping(value = "")
    String main() {
        return "redirect:/blog";
    }

    @GetMapping(value = "/blog")
    String blog(@RequestParam(value = "label", required = false) String label,
                @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                Model model) {
        BlogVO blogVO;
        if (label != null) {
            blogVO = frontService.getPageByLabel(label, page);
        } else {
            blogVO = frontService.getMainPage(page);
        }
        model.addAttribute(DATA, blogVO);
        return "front/blog";
    }

    @GetMapping(value = "/about")
    String about() {
        return "front/about";
    }

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

    @PostMapping(value = "put-comment")
    public String createComment(@Email String email, String detail, Integer articleId) {
        frontService.createComment(email, detail, articleId);
        return "redirect:/detail/" + articleId;
    }

}
