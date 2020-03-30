package com.aagu.blog.Services

import com.aagu.blog.Models.Log
import com.aagu.blog.Models.PageModel

interface LogService {
    fun writeLog(log: Log): Int

    fun getLogs(page: Int, limit: Int): List<Log>

    fun getPage(page: Int, limit: Int): PageModel<Log>
}