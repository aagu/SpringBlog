package com.aagu.blog.service.impl

import com.aagu.blog.dao.UserDao
import com.aagu.blog.model.User
import com.aagu.blog.exception.NotFoundException
import com.aagu.blog.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userDao: UserDao) : UserService {
    override fun getSec(name: String): User {
        return userDao.getByName(name)
    }

    @Throws(NotFoundException::class)
    override fun info(name: String): User {
        return userDao.getByName(name) ?: throw NotFoundException("user with name: $name not exist")
    }
}