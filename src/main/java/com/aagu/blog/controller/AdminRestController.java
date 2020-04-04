package com.aagu.blog.controller;

import com.aagu.blog.Dao.LabelDao;
import com.aagu.blog.Models.*;
import com.aagu.blog.service.AdminService;
import com.aagu.blog.service.FileService;
import com.aagu.blog.service.FrontService;
import com.aagu.blog.Utils.HttpUtil;
import com.aagu.blog.Views.ArticleEditVO;
import com.aagu.blog.Views.TagTree;
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
    private FileService fileService;

    @Autowired
    private LabelDao labelDao;

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

    @GetMapping("article/list")
    public Object articleList(@RequestParam(value = "page", defaultValue = "1")Integer page,
                              @RequestParam(value = "limit", defaultValue = "10")Integer limit) {
        PageModel<Article> articles = adminService.getArticleByPage(page, limit);
        return HttpUtil.createResponse(20000, null, articles);
    }

    @GetMapping("article/detail")
    public Object articleDetail(@RequestParam("id")Integer id) {
        Article article = frontService.getArticleById(id);
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

    @PostMapping("article/create")
    public Object articleCreate(@RequestBody Article article){
        adminService.addArticle(article);
        return HttpUtil.createResponse(20000, null, "OK");
    }

    @PostMapping("article/update")
    public Object articleUpdate(@RequestBody Article article){
        adminService.updateArticle(article);
        return HttpUtil.createResponse(20000, null, "OK");
    }

    @PutMapping("article/{id}")
    public Object articlePublish(@PathVariable("id")Integer id) {
        if (id > 0) {
            adminService.publishArticle(id);
            return HttpUtil.createResponse(20000, null, "OK");
        }
        return HttpUtil.createResponse(40000, "article no exist", null);
    }

    @DeleteMapping("article/{id}")
    public Object articleDelete(@PathVariable("id")Integer id) {
        if (id > 0) {
            adminService.deleteArticle(id);
            return HttpUtil.createResponse(20000, null, "OK");
        }
        return HttpUtil.createResponse(40000, "article no exist", null);
    }

    @GetMapping("user/list")
    public Object userList(@RequestParam(value = "page", defaultValue = "1")Integer page) {
        List<User> users = adminService.getUserByPage(page);
        return HttpUtil.createResponse(20000, null, users);
    }

    @GetMapping("resource/list")
    public Object resourceList(@RequestParam(value = "page", required = false, defaultValue = "1")Integer page,
                               @RequestParam(value = "limit", required = false)Integer limit,
                               @RequestParam(value = "type")String type) {
        List<Resource> resources = fileService.getByType(type);
        return HttpUtil.createResponse(20000, null, resources);
    }

    @PostMapping("resource/update")
    public Object resourceUpdate(@RequestBody Resource resource) {
        fileService.updateRes(resource);
        return HttpUtil.createResponse(20000, null, "ok");
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
        List<Label> labels = adminService.getAllLabels();
        return HttpUtil.createResponse(20000, null, labels);
    }

    @PostMapping("label/add")
    public Object labelAdd(@RequestBody TagTree node) {
        Map<String, Object> map = adminService.addLabel(node.getName(), node.getParentId());
        if (map == null) return HttpUtil.createResponse(400000, "error add label", null);
        Set<TagTree> tagTree = new HashSet<>();
        tagTree.add(((TagTree)map.get("tree")));
        map.replace("tree", tagTree);
        return HttpUtil.createResponse(20000, null, map);
    }

    @PutMapping("label/{name}")
    public Object labelUpdate(@PathVariable("name")String name, @RequestBody TagTree node) {
        Map<String, Object> map = adminService.updateLabelName(name, node.getId());
        if (map == null) return HttpUtil.createResponse(400000, "error update label", null);
        Set<TagTree> tagTree = new HashSet<>();
        tagTree.add(((TagTree)map.get("tree")));
        map.replace("tree", tagTree);
        return HttpUtil.createResponse(20000, null, map);
    }

    @DeleteMapping("label/{name}")
    public Object labelDelete(@PathVariable("name")String name) {
        Map<String, Object> map = adminService.deleteLabel(name);
        if (map == null) return HttpUtil.createResponse(400000, "error delete label", null);
        Set<TagTree> tagTree = new HashSet<>();
        tagTree.add(((TagTree)map.get("tree")));
        map.replace("tree", tagTree);
        return HttpUtil.createResponse(20000, null, map);
    }

    @GetMapping("search/user")
    public Object searchUser(@RequestParam("name")String name) {
        List<String> names = adminService.getAllUserByName(name);
        return HttpUtil.createResponse(20000, null, names);
    }
}
