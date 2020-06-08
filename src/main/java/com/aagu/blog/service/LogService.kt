package com.aagu.blog.service

import com.aagu.blog.model.Log
import com.aagu.blog.model.PageModel

interface LogService {
    fun writeLog(log: Log): Int

    fun getLogs(page: Int, limit: Int): List<Log>

    fun getPage(page: Int, limit: Int): PageModel<Log>
}