package com.aagu.blog.service.impl

import com.aagu.blog.dao.NoticeDao
import com.aagu.blog.exception.ModificationFailedException
import com.aagu.blog.model.Notice
import com.aagu.blog.model.PageModel
import com.aagu.blog.service.NoticeService
import com.aagu.blog.util.Pager
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class NoticeServiceImpl(
        private val noticeDao: NoticeDao
) : NoticeService {
    private val logger = LoggerFactory.getLogger("NoticeService")

    override fun getNoticeByPage(page: Int, num: Int, params: Map<String, String>): PageModel<Notice> {
        return Pager(noticeDao).getPage(page, num, params)
    }

    override fun createNotice(notice: Notice): Notice {
        notice.date = Date()
        try {
            val res = noticeDao.insertNotice(notice)
            if (res != 1) throw ModificationFailedException("failed to add article: no row bean inserted")
        } catch (ex: Exception) {
            logger.error("failed to add notice", ex)
            throw ModificationFailedException("failed to add notice: ${ex.message}")
        }
        return notice
    }
}