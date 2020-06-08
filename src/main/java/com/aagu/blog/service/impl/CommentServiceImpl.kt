package com.aagu.blog.service.impl

import com.aagu.blog.common.CommentVisibility
import com.aagu.blog.dao.CommentDao
import com.aagu.blog.model.Comment
import com.aagu.blog.service.CommentService
import com.aagu.blog.view.CommentVO
import org.springframework.stereotype.Service
import kotlin.math.ceil

@Service
open class CommentServiceImpl(private val commentDao: CommentDao) : CommentService {

    override fun getCommentCount(): Int {
        return commentDao.getCount(1)
    }

    override fun addComment(comment: Comment): Int {
        return commentDao.insertComment(comment.detail, comment.email, comment.articleId)
    }

    override fun getComments(articleId: Int?, page: Int, limit: Int, visibility: CommentVisibility): CommentVO {
        val queryMap: HashMap<String, String> = HashMap()
        if (articleId != null) {
            queryMap["articleId"] = articleId.toString()
        }
        when (visibility) {
            CommentVisibility.VISIBLE -> {
                queryMap["status_not"] = "deleted"
            }
            CommentVisibility.INVISIBLE -> {
                queryMap["status"] = "delete"
            }
            CommentVisibility.ALL -> {
                // no constrain here
            }
            else -> {
                queryMap["status"] = visibility.toString().toLowerCase()
            }
        }
        val count = commentDao.getCountV2(queryMap)
        queryMap["start"] = ((page - 1) * limit).toString()
        queryMap["num"] = limit.toString()
        val commentVO = CommentVO()
        commentVO.comments = commentDao.getByPageV2(queryMap)
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