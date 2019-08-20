package com.aagu.blog.Controllers;

import com.aagu.blog.Common.ServerResponse;
import com.aagu.blog.Models.Comment;
import com.aagu.blog.Services.AdminService;
import com.aagu.blog.Services.FileService;
import com.aagu.blog.Services.FrontService;
import com.aagu.blog.Utils.TextUtil;
import com.aagu.blog.Views.CommentVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    private final FileService fileService;

    private static final String DATA = "data";

    public AdminController(AdminService adminService, FrontService frontService, FileService fileService) {
        this.adminService = adminService;
        this.fileService = fileService;
    }

    /**
     * admin主页
     * @return admin主页
     */
    @GetMapping(value = "")
    public String admin() {
        return "admin/index";
    }

    /**
     * 登录页面
     * @return 登录页
     */

    @GetMapping(value = "/login")
    public String login() {
        return "admin/index";
    }


    @GetMapping(value = "/resources-tem")
    public String resourceTem(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model) {
        model.addAttribute("data", fileService.getByPage(page));
        return "admin/resource-tem";
    }

    /**
     * 标签管理页面
     * @param model ThymeLeaf Model
     * @return 标签管理页
     */
    @GetMapping(value = "/labels-tem")
    public String labelManageTem(Model model) {
        model.addAttribute(DATA, adminService.getAllLabels());
        return "admin/label-tem";
    }

    /**
     * 评论页面
     * @param search 搜索关键词
     * @param sort 排序类型
     * @param page 页码
     * @param model ThymeLeaf Model
     * @return 评论页
     */
    @GetMapping("/comment")
    public String getCommentTem(@RequestParam(value = "search", required = false) String search,
                                @RequestParam(value = "sort", defaultValue = "0") Integer sort,
                                @RequestParam(value = "page", defaultValue = "1") Integer page, Model model) {
        if (!sort.equals(0) && !sort.equals(1)) {
            sort = 0;
        }
        String order = "desc";
        if (sort.equals(1)) {
            order = "asc";
        }
        List<Comment> comments;
        if (search == null || search.isEmpty()) {
            comments = adminService.getCommentByPage(
                    page,
                    null,
                    order);
        } else {
            comments = adminService.getCommentByPage(
                    page,
                    search,
                    order);
        }
        for (Comment comment : comments) {
            comment.setArticleTitle(TextUtil.cutString(comment.getArticleTitle(), 10));
            comment.setDetail(TextUtil.cutString(comment.getDetail(), 10));
        }

        CommentVO commentVO = new CommentVO();
        commentVO.setComments(comments);
        commentVO.setCurrePage(page);
        commentVO.setTotalPage(adminService.getCommentPages());

        String param = "?sort=" + sort;
        if (search != null) {
            param += "&search=" + search;
        }

        model.addAttribute("urlParam", param);
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        model.addAttribute(DATA, commentVO);
        return "admin/comment-tem";
    }

    /**
     * 删除文章
     * @param id 文章ID
     * @return JSON对象
     */
    @DeleteMapping(value = "/delete-article")
    public ServerResponse deleteArticle(Integer id) {
        return adminService.deleteArticle(id);
    }

    /**
     * 评论已读标记
     * @param id 评论ID
     * @return JSON对象
     */
    @PostMapping(value = "/read-comment")
    @ResponseBody
    public ServerResponse readComment(Integer id) {
        return adminService.markCommentAsRead(id);
    }

    /**
     * 账户管理页面
     * @return 账户管理页
     */
    @GetMapping(value = "account-manage")
    public String account() {
        return "admin/account-tem";
    }

    /**
     * 删除评论
     * @param id 评论ID
     * @return JSON对象
     */
    @DeleteMapping(value = "/del-comment")
    @ResponseBody
    public ServerResponse deleteComment(Integer id) {
        return adminService.deleteComment(id);
    }

    @PostMapping(value = "logout")
    public void logout() {
        adminService.logout();
    }
}
