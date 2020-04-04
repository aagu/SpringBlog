package com.aagu.blog.service.impl

import com.aagu.blog.Dao.ArticleDao
import com.aagu.blog.service.ArticleService
import org.springframework.stereotype.Service

@Service
class ArticleServiceImpl(private val articleDao: ArticleDao) : ArticleService {
    override fun getArticleCount(): Int {
        return articleDao.articleCount
    }
}