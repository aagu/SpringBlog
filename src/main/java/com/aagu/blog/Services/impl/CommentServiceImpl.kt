package com.aagu.blog.Services.impl

import com.aagu.blog.Dao.CommentDao
import com.aagu.blog.Models.Comment
import com.aagu.blog.Services.CommentService
import com.aagu.blog.Views.CommentVO
import org.springframework.stereotype.Service
import kotlin.math.ceil

@Service
class CommentServiceImpl(private val commentDao: CommentDao) : CommentService {

    override fun getCommentCount(): Int {
        return commentDao.getCount(1)
    }

    override fun addComment(comment: Comment): Int {
        return commentDao.insertComment(comment.detail, comment.email, comment.articleId)
    }

    override fun getComments(articleId: Int?, page: Int, limit: Int): CommentVO {
        val count = if (articleId != null) commentDao.getCountV2(mapOf(Pair("articleId", articleId.toString())))
            else commentDao.getCountV2(mapOf())
        val map: HashMap<String, String> = HashMap()
        map["start"] = ((page - 1) * limit).toString()
        map["num"] = limit.toString()
        articleId?.let {
            map["articleId"] = it.toString()
        }
        val commentVO = CommentVO()
        commentVO.comments = commentDao.getByPageV2(map)
        commentVO.totalPage = ceil((count.toDouble() / limit)).toInt()
        commentVO.currePage = page
        return commentVO
    }

    override fun markRead(id: Int): Int {
        return commentDao.markAsRead(id);
    }

    override fun markDelete(id: Int): Int {
        return commentDao.deleteById(id)
    }
}