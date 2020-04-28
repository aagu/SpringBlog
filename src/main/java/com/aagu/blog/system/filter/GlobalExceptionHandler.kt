package com.aagu.blog.system.filter

import com.aagu.blog.exception.ModificationFailedException
import com.aagu.blog.exception.NotFoundException
import com.aagu.blog.util.HttpUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ModificationFailedException::class)
    @ResponseBody
    fun handleModificationFailure(ex: ModificationFailedException): Any {
        val body = HttpUtil.createResponse(51000, ex.message, null)
        return ResponseEntity(body, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseBody
    fun handleNotFoundException(ex: NotFoundException): Any {
        val body = HttpUtil.createResponse(50080, ex.message, null)
        return ResponseEntity(body, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}