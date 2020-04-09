package com.aagu.blog.util

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

class RequestHolder {
    companion object {
        private val requestLocal = ThreadLocal<HttpServletRequest>()
        private val responseLocal = ThreadLocal<HttpServletResponse>()

        fun getRequest(): HttpServletRequest {
            return requestLocal.get()
        }

        fun setRequest(request: HttpServletRequest) {
            requestLocal.set(request)
        }

        fun getResponse(): HttpServletResponse {
            return responseLocal.get()
        }

        fun setResponse(response: HttpServletResponse) {
            responseLocal.set(response)
        }

        fun getSession(): HttpSession {
            return requestLocal.get().session
        }

        fun remove() {
            responseLocal.remove()
            requestLocal.remove()
        }
    }
}