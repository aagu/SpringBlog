package com.aagu.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TemplateController {

    @RequestMapping(value = "blog")
    String main(HttpServletRequest request) {
        request.setAttribute("key", "hello world");
        return "index";
    }

    @RequestMapping(value = "admin")
    String admin(HttpServletRequest request) {
        request.setAttribute("greeting", "Hi");
        request.setAttribute("key", "admin");
        return "admin";
    }
}
