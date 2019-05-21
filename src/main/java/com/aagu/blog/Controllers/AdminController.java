package com.aagu.blog.Controllers;

import com.aagu.blog.Models.Article;
import com.aagu.blog.Models.User;
import com.aagu.blog.ServerResponse;
import com.aagu.blog.Services.AdminService;
import com.aagu.blog.Services.FrontService;
import com.aagu.blog.Utils.TextUtil;
import com.aagu.blog.Views.ArticleEditVO;
import com.aagu.blog.Views.ArticleManageVO;
import com.aagu.blog.Views.CommentVO;
import com.aagu.blog.Views.LabelManageVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    FrontService frontService;

    private static final String DATA = "data";

    @GetMapping(value = "")
    public String admin() {
        return "admin/admin";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "admin/login";
    }

    @PostMapping(value = "/login-from")
    public String loginForm(String name, String pwd, RedirectAttributes attributes, HttpSession session) {
        ServerResponse res = adminService.login(name, pwd);
        if (res.isSuccess()) {
            session.setAttribute("CurrentAdmin", name);
            return "redirect:/admin";
        }
        attributes.addFlashAttribute("login_msg", res.getMsg());
        return "redirect:/admin/login";
    }

    @GetMapping(value = "/main")
    public String mainTem(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model) {
        model.addAttribute(DATA, adminService.getMainAdminPage(page));
        return "admin/main-tem";
    }

    @GetMapping(value = "/resources")
    public String resourceTem(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model) {
        //model.addAttribute("data", null);
        return "admin/resource-tem";
    }

    @GetMapping(value = "/content-manage")
    public String articleManage(@RequestParam(value = "labelId", required = false) Integer labelId,
                                @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                @RequestParam(value = "search", required = false) String search, Model model, HttpServletRequest request) {
        if (labelId == null && search == null) {
            //错误了 不能都为空
            model.addAttribute("defaultData", "参数错误啦！！");
            return "error";
        }

        String requestUrl = "";

        ArticleManageVO manageVO = new ArticleManageVO();
        manageVO.setLabels(adminService.getAllFinalLabels());
        manageVO.setArticles(adminService.getAllArticles());
        if (labelId != null) {
            requestUrl = request.getContextPath() + "/admin/content-manage?labelId=" + labelId;
        }
        manageVO.setRequestUrl(requestUrl);
        model.addAttribute(DATA, manageVO);
        return "admin/articleManage-tem";
    }

    @GetMapping(value = "/labels-tem")
    public String labelManageTem(Model model) {
        model.addAttribute(DATA, adminService.getAllLabels());
        return "admin/label-tem";
    }

    @ResponseBody
    @RequestMapping(value = "/labels-data", headers = {"content-Type=application/json"})
    public Set<LabelManageVO> getLabelsData() {
        ServerResponse<Set<LabelManageVO>> response = adminService.getTreeViewData();
        return response.getData();
    }

    @RequestMapping(value = "/write-article")
    public String handleWrite(@RequestParam(value = "id", required = false) Integer id,
                              Model model) {
        ArticleEditVO articleEditVO = new ArticleEditVO();
        articleEditVO.setLabels(adminService.getAllFinalLabels());
        //编辑现有文章
        if (id != null) {
            articleEditVO.setId(id);
            Article article = frontService.getArticleById(id).getData();
            articleEditVO.setLabelId(article.getLabelId());
            articleEditVO.setTitle(article.getTitle());
            articleEditVO.setDetail(article.getDetail());
        }

        model.addAttribute(DATA, articleEditVO);

        return "admin/markdown-tem";
    }

    @PutMapping("/create-update-content")
    @ResponseBody
    public ServerResponse createAndUpdateContent(Article article) {
        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        article.setDate(df.format(now));
        if (article.getId() != null) {
            ServerResponse<Article> data = frontService.getArticleById(article.getId());
            if (data.isSuccess()) {
                //如果找到了 有这个文章说明是更新操作
                return adminService.updateArticle(article);
            } else {
                //否则就是插入的操作
                return adminService.publishArticle(article);
            }
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

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
        List<CommentVO> VOs = adminService.getAllComments();
        for (CommentVO vo : VOs) {
            vo.setArticleTitle(TextUtil.cutString(vo.getArticleTitle(), 10));
            vo.setDetail(TextUtil.cutString(vo.getDetail(), 10));
        }

        String param = "?sort=" + sort;
        if (search != null) {
            param += "&search=" + search;
        }

        model.addAttribute("urlParam", param);
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        model.addAttribute(DATA, VOs);
        return "admin/comment-tem";
    }

    @DeleteMapping(value = "/delete-article")
    public ServerResponse deleteArticle(Integer id) {
        return adminService.deleteArticle(id);
    }

    @PostMapping(value = "/read-comment")
    public ServerResponse deleteComment(Integer id) {
        return adminService.markCommentAsRead(id);
    }

    @GetMapping(value = "account-manage")
    public String account() {
        return "admin/account-tem";
    }
}
