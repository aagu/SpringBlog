package com.aagu.blog.controller;

import com.aagu.blog.service.FrontService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class FrontController {

    private final FrontService frontService;

    private static final String DATA = "data";
    private static final String DEFAULT_RESULT = "defaultData";
    public static final String KEY_READ_RECORD = "readCount";

    public FrontController(FrontService frontService) {
        this.frontService = frontService;
    }

    /**
     * 主页
     * @return 重定向到博客页
     */
    @GetMapping(value = "")
    public String index() {
        return "index";
    }

    @GetMapping("/front")
    public String main() {
        return "front";
    }
//
//    @GetMapping(value = "/about")
//    public String about() {
//        return "front/about";
//    }

//    /**
//     * 评论文章
//     * @param email 评论者邮箱
//     * @param detail 评论详情
//     * @param articleId 文章编号
//     * @return 当前文章详情页
//     */
//    @PostMapping(value = "/put-comment")
//    public String createComment(@Email String email, String detail, Integer articleId) {
//        frontService.createComment(email, detail, articleId);
//        return "redirect:/detail/" + articleId;
//    }

}
