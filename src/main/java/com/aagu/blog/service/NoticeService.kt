package com.aagu.blog.service

import com.aagu.blog.model.Notice
import com.aagu.blog.model.PageModel

interface NoticeService {
    fun getNoticeByPage(page: Int, num: Int, params: Map<String, String>): PageModel<Notice>
    fun createNotice(notice: Notice): Notice
    fun updateNotice(notice: Notice): Notice
    fun deleteNotice(notice: Notice): Notice
}