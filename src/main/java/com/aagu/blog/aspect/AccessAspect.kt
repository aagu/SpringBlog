package com.aagu.blog.aspect

import com.aagu.blog.Common.Const.USER_LOGIN
import com.aagu.blog.Common.Const.USER_LOGOUT
import com.aagu.blog.Dao.LogDao
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@Aspect
class AccessAspect {

    @Autowired
    private lateinit var logDao: LogDao

    @After(value = "execution(* com.aagu.blog.controller.AdminRestController.login(*))")
    fun login() {
        logDao.appendLog("log in successfully", USER_LOGIN)
    }

    @After(value = "execution(* com.aagu.blog.controller.AdminRestController.logout())")
    fun logout() {
        logDao.appendLog("log out", USER_LOGOUT)
    }
}