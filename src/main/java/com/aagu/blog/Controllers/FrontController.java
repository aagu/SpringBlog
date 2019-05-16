package com.aagu.blog.Controllers;

import com.aagu.blog.Models.Article;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FrontController {

    @GetMapping(value = "")
    String main() {
        return "index";
    }

    @GetMapping(value = "index")
    void index(HttpServletResponse response) {
        try {
            response.sendRedirect("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "blog")
    String blog(Model model) {
        Article article1 = new Article("blog", "2019-03-12", 1, "detail of the article");
        Article article2 = new Article("blog", "2019-05-14", 1, "today is cloudy");
        List<Article> articleList = new ArrayList<>();
        articleList.add(article1);
        articleList.add(article2);
        model.addAttribute("articles", articleList);
        return "blog";
    }

    @GetMapping(value = "about")
    String about() {
        return "about";
    }

    @GetMapping(value = "contact")
    String contact() {
        return "contact";
    }

}
