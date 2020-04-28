package com.aagu.blog.service

import com.aagu.blog.Models.Article
import com.aagu.blog.Models.PageModel
import com.aagu.blog.exception.NotFoundException
import com.aagu.blog.view.ArticleDetailVO

interface ArticleService {
    fun getArticleCount(): Int

    fun getArticleByPage(page: Int, limit: Int, params: Map<String, String>): PageModel<Article>

    fun addArticle(article: Article): Article

    fun updateArticle(article: Article): Article

    fun deleteArticle(id: Int): Int

    fun getArticleById(id: Int): Article?

    @Throws(NotFoundException::class)
    fun getViewDetail(id: Int): ArticleDetailVO

    @Throws(NotFoundException::class)
    fun publishArticle(id: Int)
}