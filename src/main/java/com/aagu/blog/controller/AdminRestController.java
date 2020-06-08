package com.aagu.blog.controller;

import com.aagu.blog.dao.LabelDao;
import com.aagu.blog.model.Article;
import com.aagu.blog.model.Comment;
import com.aagu.blog.model.Label;
import com.aagu.blog.model.User;
import com.aagu.blog.exception.NotFoundException;
import com.aagu.blog.service.AdminService;
import com.aagu.blog.service.ArticleService;
import com.aagu.blog.service.FrontService;
import com.aagu.blog.service.LabelService;
import com.aagu.blog.util.HttpUtil;
import com.aagu.blog.view.ArticleEditVO;
import com.aagu.blog.view.TagTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private FrontService frontService;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private LabelService labelService;

    @GetMapping("user/info")
    public Object info(@RequestParam(value = "token", required = false) String token) {
        if (token == null || token.isEmpty()) {
            return HttpUtil.createResponse(20000, null, "用户令牌错误");
        }
        Map<String, String> params = adminService.getUserInfo(token);
        if (params == null) {
            return HttpUtil.createResponse(41000, "token expired", null);
        }
        return HttpUtil.createResponse(20000, null, params);
    }

    @PostMapping("user/login")
    public Object login(@RequestBody Map<String, String> token) {
        String sessionId = adminService.login(token.get("username"), token.get("password"));
        //String role = "admin";
        Map<String, String> params = new HashMap<>();
        params.put("user", token.get("username"));
        params.put("token", sessionId);
        return HttpUtil.createResponse(20000, null, params);
    }

    @PostMapping("user/logout")
    public Object logout() {
        //Subject subject = SecurityUtils.getSubject();
        //subject.logout();
        return HttpUtil.createResponse(20000, null, "success");
    }

    @GetMapping("comment/list")
    public Object commentList(@RequestParam(value = "status", required = false) String status,
                              @RequestParam(value = "page", required = false, defaultValue = "1")Integer page,
                              @RequestParam(value = "limit", required = false, defaultValue = "20")Integer limit,
                              @RequestParam(value = "sort", required = false) String sort) {
        List<Comment>  comments = adminService.getCommentByPage(page, null, "desc", status);
        return HttpUtil.createResponse(20000, null, comments);
    }

//    @GetMapping("article/list")
//    public Object articleList(@RequestParam(value = "page", defaultValue = "1")Integer page,
//                              @RequestParam(value = "limit", defaultValue = "10")Integer limit) {
//        PageModel<Article> articles = articleService.getArticleByPage(page, limit);
//        return HttpUtil.createResponse(20000, null, articles);
//    }

    @GetMapping("article/detail")
    public Object articleDetail(@RequestParam("id")Integer id) {
        Article article = articleService.getArticleById(id);
        if (article == null) return null;
        ArticleEditVO vo = new ArticleEditVO();
        vo.setContent(article.getContent());
        vo.setTitle(article.getTitle());
        vo.setLabelId(article.getLabelId());
        vo.setLabelName(labelDao.getById(article.getLabelId()).getName());
        vo.setId(article.getId());
        vo.setStatus(article.getStatus());
        vo.setDate(article.getDate());
        return HttpUtil.createResponse(20000, null, vo);
    }

    @PutMapping("article/{id}")
    public Object articlePublish(@PathVariable("id")Integer id) {
        try {
            articleService.publishArticle(id);
            return HttpUtil.createResponse(20000, null, "OK");
        } catch (NotFoundException e) {
            return HttpUtil.createResponse(40000, "article no exist", null);
        }
    }

    @DeleteMapping("article/{id}")
    public Object articleDelete(@PathVariable("id")Integer id) {
        if (id > 0) {
            articleService.deleteArticle(id);
            return HttpUtil.createResponse(20000, null, "OK");
        }
        return HttpUtil.createResponse(40000, "article no exist", null);
    }

    @GetMapping("user/list")
    public Object userList(@RequestParam(value = "page", defaultValue = "1")Integer page) {
        List<User> users = adminService.getUserByPage(page);
        return HttpUtil.createResponse(20000, null, users);
    }

    @GetMapping("label/tree")
    public Object labelTree(@RequestParam(value = "needRoot", defaultValue = "false")Boolean needRoot) {
        Set<TagTree> tagTree = new HashSet<>();
        Map<String, Object> map = adminService.getTreeViewData();
        if (!needRoot) {
            tagTree.addAll(((TagTree)map.get("tree")).getChildren());
        } else {
            tagTree.add(((TagTree)map.get("tree")));
        }
        map.replace("tree", tagTree);
        return HttpUtil.createResponse(20000, null, map);
    }

    @GetMapping("labels")
    public Object labels() {
        List<Label> labels = labelService.getAll();
        return HttpUtil.createResponse(20000, null, labels);
    }

    @PostMapping("label")
    public Object labelAdd(@RequestBody Label label) {
        labelService.add(label);
        return HttpUtil.createResponse(20000, null, label);
    }

    @PutMapping("label")
    public Object labelUpdate(@RequestBody Label label) {
        labelService.modify(label);
        return HttpUtil.createResponse(20000, null, label);
    }

    @DeleteMapping("label")
    public Object labelDelete(@RequestParam("id")Integer id) {
        labelService.delete(id);
        return HttpUtil.createResponse(20000, null, "ok");
    }

    @GetMapping("search/user")
    public Object searchUser(@RequestParam("name")String name) {
        List<String> names = adminService.getAllUserByName(name);
        return HttpUtil.createResponse(20000, null, names);
    }
}
