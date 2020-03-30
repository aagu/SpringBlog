package com.aagu.blog.Services

import com.aagu.blog.Models.Comment
import com.aagu.blog.Views.CommentVO

interface CommentService {
    fun getCommentCount(): Int

    fun addComment(comment: Comment): Int

    fun getComments(articleId: Int?, page: Int, limit: Int): CommentVO
}