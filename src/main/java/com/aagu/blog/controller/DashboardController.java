package com.aagu.blog.controller;

import com.aagu.blog.aspect.Auth;
import com.aagu.blog.service.ArticleService;
import com.aagu.blog.service.CommentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
class DashboardController {
    private final CommentService commentService;

    private final ArticleService articleService;

    public DashboardController(CommentService commentService, ArticleService articleService) {
        this.commentService = commentService;
        this.articleService = articleService;
    }

    @GetMapping("/dashboard/statistic")
    @Auth
    public Object statistic() {
        Map<String, String> resp = new HashMap<>();
        resp.put("visitor","120");
        resp.put("comment", String.valueOf(commentService.getCommentCount()));
        resp.put("article", String.valueOf(articleService.getArticleCount()));
        resp.put("resource", "8");
        return resp;
    }
}