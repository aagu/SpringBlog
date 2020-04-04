package com.aagu.blog.service

import com.aagu.blog.Common.CommentVisibility
import com.aagu.blog.Models.Comment
import com.aagu.blog.Views.CommentVO

interface CommentService {
    fun getCommentCount(): Int

    fun addComment(comment: Comment): Int

    fun getComments(articleId: Int?, page: Int, limit: Int, visibility: CommentVisibility): CommentVO

    fun markRead(id: Int): Int

    fun markDelete(id: Int): Int
}