package com.aagu.blog.service.impl

import com.aagu.blog.Dao.LogDao
import com.aagu.blog.Models.Log
import com.aagu.blog.Models.PageModel
import com.aagu.blog.service.LogService
import org.springframework.stereotype.Service

@Service
class LogServiceImpl(private val logDao: LogDao) : LogService {
    override fun writeLog(log: Log): Int {
        return logDao.appendLog(log.info, log.reason)
    }

    override fun getLogs(page: Int, limit: Int): List<Log> {
        return logDao.getLog(mapOf(Pair("start", ((page - 1) * limit).toString()), Pair("num", limit.toString())))
    }

    override fun getPage(page: Int, limit: Int): PageModel<Log> {
        TODO("Not yet implemented")
    }
}