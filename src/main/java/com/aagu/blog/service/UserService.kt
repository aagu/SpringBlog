package com.aagu.blog.service

import com.aagu.blog.model.User
import com.aagu.blog.exception.NotFoundException

interface UserService {
    fun getSec(name: String): User

    @Throws(NotFoundException::class)
    fun info(name: String): User
}