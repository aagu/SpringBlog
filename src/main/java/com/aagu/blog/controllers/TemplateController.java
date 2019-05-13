package com.aagu.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class TemplateController {

    @RequestMapping(value = "")
    String main() {
        return "index";
    }

    @RequestMapping(value = "index")
    void index(HttpServletResponse response) {
        try {
            response.sendRedirect("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "admin")
    String admin(HttpServletRequest request) {
        request.setAttribute("greeting", "Hi");
        request.setAttribute("key", "admin");
        return "admin";
    }

    @RequestMapping(value = "blog")
    String blog() {
        return "blog";
    }

    @RequestMapping(value = "about")
    String about() {
        return "about";
    }

    @RequestMapping(value = "contact")
    String contact() {
        return "contact";
    }
}
