package com.aagu.blog.controller

import com.aagu.blog.Models.User
import com.aagu.blog.Services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("user")
class UserController {
    @Autowired
    private lateinit var userService: UserService

    @GetMapping("sec")
    fun getUser(@RequestParam("name") name: String): User {
        return userService.getSec(name)
    }
}