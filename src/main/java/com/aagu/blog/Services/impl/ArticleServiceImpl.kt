package com.aagu.blog.Services.impl

import com.aagu.blog.Dao.ArticleDao
import com.aagu.blog.Services.ArticleService
import org.springframework.stereotype.Service

@Service
class ArticleServiceImpl(private val articleDao: ArticleDao) : ArticleService {
    override fun getArticleCount(): Int {
        return articleDao.articleCount
    }
}