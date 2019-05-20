package com.aagu.blog.Controllers;

import com.aagu.blog.Models.Article;
import com.aagu.blog.ServerResponse;
import com.aagu.blog.Services.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class FrontController {

    @Autowired
    FrontService frontService;

    private static final String DATA = "data";
    private static final String DEFAULT_RESULT = "defaultData";

    @GetMapping(value = "")
    void main(HttpServletResponse response) {
        try {
            response.sendRedirect("blog");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/blog")
    String blog(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                @RequestParam(value = "end", required = false, defaultValue = "3") Integer end,
                Model model) {
        model.addAttribute(DATA, frontService.getMainPage());
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
        ServerResponse<Article> article = frontService.getArticleById(index);
        if (!article.isSuccess()) {
            model.addAttribute(DEFAULT_RESULT, "找不到文章");
            return "error";
        }
        model.addAttribute(DATA, article);
        return "front/detail";
    }

}
