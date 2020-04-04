package com.aagu.blog.controller

import com.aagu.blog.service.LogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/logs")
class LogController {
    @Autowired
    private lateinit var logService: LogService

    @GetMapping("")
    fun getLog(@RequestParam("page", defaultValue = "1") page: Int,
               @RequestParam("limit", defaultValue = "10") limit: Int): Any {
        return logService.getLogs(page, limit)
    }
}