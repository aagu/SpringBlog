package com.aagu.blog.config;

import com.aagu.blog.Utils.CountUtil;
import com.aagu.blog.controller.FrontController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;

@Configuration
@WebListener
public class VisitorCounter implements HttpSessionListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.info("用户上线");
        se.getSession().setAttribute(FrontController.KEY_READ_RECORD, new HashSet<Integer>());
        CountUtil.plus();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("用户下线");
        CountUtil.sub();
    }
}
