package com.aagu.blog.controller

import com.aagu.blog.model.Notice
import com.aagu.blog.model.PageModel
import com.aagu.blog.service.NoticeService
import com.aagu.blog.util.HttpUtil
import com.aagu.blog.util.TextUtil
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notice")
class NoticeController(private val noticeService: NoticeService) {
    @GetMapping("/list")
    fun getList(
            @RequestParam("page", defaultValue = "1") page: Int,
            @RequestParam("limit", defaultValue = "10") limit: Int,
            @RequestParam("status", required = false) status: String?
    ): PageModel<Notice> {
        val queryMap: HashMap<String, String> = HashMap()
        if (TextUtil.notEmpty(status)) {
            queryMap["status"] = status!!
        }
        return noticeService.getNoticeByPage(page, limit, queryMap)
    }

    @PostMapping("")
    fun createNotice(@RequestBody notice: Notice): Any {
        noticeService.createNotice(notice)
        return HttpUtil.createResponse(20000, null, notice)
    }

    @PutMapping("")
    fun updateNotice(@RequestBody notice: Notice): Any {
        noticeService.updateNotice(notice)
        return HttpUtil.createResponse(20000, null, notice)
    }

    @DeleteMapping("")
    fun deleteNotice(@RequestBody notice: Notice): Any {
        noticeService.deleteNotice(notice)
        return HttpUtil.createResponse(20000, null, notice)
    }
}