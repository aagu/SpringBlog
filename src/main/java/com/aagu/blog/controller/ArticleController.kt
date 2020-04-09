package com.aagu.blog.controller

import com.aagu.blog.service.ArticleService
import com.aagu.blog.util.HttpUtil
import com.aagu.blog.util.TextUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/article")
class ArticleController {
    @Autowired
    private lateinit var articleService: ArticleService

    @GetMapping("/list")
    fun getList(
            @RequestParam("page", defaultValue = "1") page: Int,
            @RequestParam("limit", defaultValue = "10") limit: Int,
            @RequestParam("search", required = false) keyword: String?
    ): Any {
        val queryMap = HashMap<String, String>()
        if (TextUtil.notEmpty(keyword)) {
            queryMap["keyword"] = keyword!!
        }
        val articles = articleService.getArticleByPage(page, limit, queryMap)
        return HttpUtil.createResponse(20000, null, articles)
    }
}