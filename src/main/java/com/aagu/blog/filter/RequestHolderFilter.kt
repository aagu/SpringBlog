package com.aagu.blog.filter

import com.aagu.blog.util.RequestHolder
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

@Component
class RequestHolderFilter : Filter {
    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        RequestHolder.setRequest((servletRequest as HttpServletRequest))
        filterChain.doFilter(servletRequest, servletResponse)
    }
}