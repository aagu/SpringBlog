package com.aagu.blog.Utils

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

class RequestHolder {
    companion object {
        private val requestLocal = ThreadLocal<HttpServletRequest>()

        fun getRequest(): HttpServletRequest {
            return requestLocal.get()
        }

        fun setRequest(request: HttpServletRequest) {
            requestLocal.set(request)
        }

        fun getSession(): HttpSession {
            return requestLocal.get().session
        }
    }
}