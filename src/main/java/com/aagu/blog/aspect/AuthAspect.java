package com.aagu.blog.aspect;

import com.aagu.blog.util.HttpUtil;
import com.aagu.blog.util.RequestHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
class AuthAspect {

    private final StringRedisTemplate redisTemplate;

    public AuthAspect(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Around("@annotation(com.aagu.blog.aspect.Auth)")
    Object validateAuth(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = RequestHolder.Companion.getRequest();
        String requestToken = request.getHeader("token");
        if (requestToken != null) {
            if (redisTemplate.hasKey(requestToken)) {
                return pjp.proceed();
            }
        }
        return HttpUtil.createResponse(41000, "invalid token", null);
    }
}