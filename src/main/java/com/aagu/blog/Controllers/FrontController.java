package com.aagu.blog.Controllers;

import com.aagu.blog.Models.Article;
import com.aagu.blog.Services.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class FrontController {

    @Autowired
    FrontService frontService;

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
    String blog(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                @RequestParam(value = "end", required = false, defaultValue = "3") Integer end,
                Model model) {
        model.addAttribute("articles", frontService.getArticleByPage(start, end));
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
