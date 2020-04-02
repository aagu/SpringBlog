package com.aagu.blog.Services

import com.aagu.blog.Models.User

interface UserService {
    fun getSec(name: String): User
}