package com.aagu.blog.service.impl

import com.aagu.blog.dao.ArticleDao
import com.aagu.blog.model.Article
import com.aagu.blog.model.PageModel
import com.aagu.blog.exception.ModificationFailedException
import com.aagu.blog.exception.NotFoundException
import com.aagu.blog.service.ArticleService
import com.aagu.blog.service.LabelService
import com.aagu.blog.util.Pager
import com.aagu.blog.view.ArticleDetailVO
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

    override fun getArticleByPage(page: Int, limit: Int, params: Map<String, String>): PageModel<Article> {
        return Pager(articleDao).getPage(page, limit, params)
    }

    @Cacheable(value = ["article"], key = "#id")
    override fun getArticleById(id: Int): Article? {
        return articleDao.getById(id)
    }

    @CachePut(value = ["article"], key = "#article.id")
    @Throws(ModificationFailedException::class)
    override fun addArticle(article: Article): Article {
        article.date = Date()
        try {
            val res = articleDao.insertArticle(article)
            if (res != 1) throw ModificationFailedException("failed to add article: no row bean inserted")
        } catch (ex: Exception) {
            throw ModificationFailedException("failed to add article: ${ex.message}")
        }
        return article
    }

    @CacheEvict(value = ["article", "articleView"], key = "#article.id")
    override fun updateArticle(article: Article): Article {
        article.date = Date()
        try {
            val res = articleDao.updateArticle(article)
            if (res != 1) throw ModificationFailedException("failed to update article: no row bean updated")
        } catch (ex: Exception) {
            throw ModificationFailedException("failed to update article: ${ex.message}")
        }
        return article
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
        articleDetailVO.prev = articleDao.getPrevPage(id) ?: 0
        articleDetailVO.next = articleDao.getNextPage(id) ?: articleDao.maxPage
        return articleDetailVO
    }

    @Throws(NotFoundException::class)
    @CacheEvict(value = ["articleView"], key = "#id")
    override fun publishArticle(id: Int) {
        articleDao.updateStatus(id, "published")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ArticleServiceImpl::class.java.simpleName)
    }
}