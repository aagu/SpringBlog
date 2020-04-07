package com.aagu.blog.service.impl

import com.aagu.blog.Dao.UserDao
import com.aagu.blog.Models.User
import com.aagu.blog.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userDao: UserDao) : UserService {
    override fun getSec(name: String): User {
        return userDao.getByName(name)
    }

    override fun info(name: String): User {
        return userDao.getByName(name);
    }
}