package com.aagu.blog.model

import java.util.*

data class Notice(val id: Int, var title: String, var detail: String, var status: String, var date: Date?) {
}