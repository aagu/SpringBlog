package com.aagu.blog.service.impl

import com.aagu.blog.Dao.ArticleDao
import com.aagu.blog.Models.Article
import com.aagu.blog.Models.PageModel
import com.aagu.blog.util.Pager
import com.aagu.blog.view.ArticleDetailVO
import com.aagu.blog.exception.NotFoundException
import com.aagu.blog.service.ArticleService
import com.aagu.blog.service.LabelService
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.*

@Service
open class ArticleServiceImpl(
        private val articleDao: ArticleDao,
        private val labelService: LabelService
) : ArticleService {

    override fun getArticleCount(): Int {
        return articleDao.articleCount
    }

    override fun getArticleByPage(page: Int, limit: Int): PageModel<Article> {
        return Pager(articleDao).getPage(page, limit, HashMap())
    }

    @Cacheable(value = ["article"], key = "#id")
    override fun getArticleById(id: Int): Article? {
        logger.info("cache miss, get article from db")
        return articleDao.getById(id)
    }

    @CachePut(value = ["article"], key = "#result", condition = "#result > 0")
    override fun addArticle(article: Article): Int {
        return articleDao.insertArticle(article.date, article.labelId, article.content, article.title)
    }

    @CacheEvict(value = ["article"], key = "#article.id")
    override fun updateArticle(article: Article): Int {
        return articleDao.updateArticle(article)
    }

    @CacheEvict(value = ["article"], key = "#id")
    override fun deleteArticle(id: Int): Int {
        return articleDao.deleteById(id)
    }

    @Throws(NotFoundException::class)
    @Cacheable(value = ["articleView"], key = "#id")
    override fun getViewDetail(id: Int): ArticleDetailVO {
        logger.info("cache miss, get article view from db")
        val article = getArticleById(id) ?: throw NotFoundException("could not found article with id $id")
        val articleDetailVO = ArticleDetailVO()
        articleDetailVO.article = article
        articleDetailVO.label = labelService.getById(article.labelId)
        articleDetailVO.allLabels = labelService.getAll()
        articleDetailVO.prev = articleDao.getPrevPage(id) ?: 0
        articleDetailVO.next = articleDao.getNextPage(id) ?: articleDao.maxPage
        return articleDetailVO
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ArticleServiceImpl::class.java.simpleName)
    }
}