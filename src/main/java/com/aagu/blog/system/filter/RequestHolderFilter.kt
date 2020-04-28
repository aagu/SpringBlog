package com.aagu.blog.system.filter

import com.aagu.blog.util.RequestHolder
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RequestHolderFilter : Filter {
    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        RequestHolder.setRequest((servletRequest as HttpServletRequest))
        RequestHolder.setResponse((servletResponse) as HttpServletResponse)
        filterChain.doFilter(servletRequest, servletResponse)
    }

    override fun destroy() {
        RequestHolder.remove()
    }
}