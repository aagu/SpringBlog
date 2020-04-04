package com.aagu.blog.controller

import com.aagu.blog.Utils.HttpUtil
import com.aagu.blog.service.ResourceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/resources")
class ResourceController {
    @Autowired
    private lateinit var resourceService: ResourceService

    @GetMapping("")
    fun getList(): Any {
        val list = resourceService.getFileList()
        return HttpUtil.createResponse(20000, null, list)
    }

    @GetMapping("/token")
    fun getToken(@RequestParam("oldKey", required = false)oldKey: String?): Any {
        val token = if (oldKey != null) {
            resourceService.getReplaceToken(oldKey)
        } else {
            resourceService.getUploadToken()
        }
        return HttpUtil.createResponse(20000, null, token)
    }
}