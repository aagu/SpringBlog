package com.aagu.blog.controller

import com.aagu.blog.Models.Comment
import com.aagu.blog.Services.CommentService
import com.aagu.blog.Utils.HttpUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
class CommentController {
    @Autowired
    private lateinit var commentService: CommentService

    @PostMapping("")
    fun createComment(@RequestBody comment: Comment) : Any {
        return if (commentService.addComment(comment) == 1) HttpUtil.createResponse(20000, "OK", null)
        else HttpUtil.createResponse(52000, "Fail to add comment", null);
    }

    @GetMapping("")
    fun getComments(
            @RequestParam("articleId", required = false) articleId: Int?,
            @RequestParam("page", defaultValue = "1") page: Int,
            @RequestParam("limit", defaultValue = "10") limit: Int
    ): Any {
        return commentService.getComments(articleId, page, limit)
    }
}