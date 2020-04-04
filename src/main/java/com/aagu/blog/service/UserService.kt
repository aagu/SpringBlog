package com.aagu.blog.service

import com.aagu.blog.Models.User

interface UserService {
    fun getSec(name: String): User
}