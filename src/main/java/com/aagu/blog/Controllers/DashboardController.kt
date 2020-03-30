package com.aagu.blog.Controllers

import com.aagu.blog.Services.ArticleService
import com.aagu.blog.Services.CommentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class DashboardController {
    @Autowired
    lateinit var commentService: CommentService

    @Autowired
    lateinit var articleService: ArticleService

    @GetMapping("/dashboard/statistic")
    fun statistic(): Any {
        val visitorCount = 120
        val commentCount = commentService.getCommentCount()
        val articleCount = articleService.getArticleCount()
        val resourceCount = 8;
        return mapOf(
                Pair("visitor", visitorCount),
                Pair("comment", commentCount),
                Pair("article", articleCount),
                Pair("resource", resourceCount)
        )
    }
}