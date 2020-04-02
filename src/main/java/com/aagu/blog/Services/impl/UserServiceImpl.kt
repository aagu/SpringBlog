package com.aagu.blog.Services.impl

import com.aagu.blog.Dao.UserDao
import com.aagu.blog.Models.User
import com.aagu.blog.Services.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userDao: UserDao) : UserService {
    override fun getSec(name: String): User {
        return userDao.getByName(name)
    }
}