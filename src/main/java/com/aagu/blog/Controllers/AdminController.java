package com.aagu.blog.Controllers;

import com.aagu.blog.Models.Article;
import com.aagu.blog.Common.ServerResponse;
import com.aagu.blog.Models.Comment;
import com.aagu.blog.Services.AdminService;
import com.aagu.blog.Services.FrontService;
import com.aagu.blog.Utils.TextUtil;
import com.aagu.blog.Views.ArticleEditVO;
import com.aagu.blog.Views.ArticleManageVO;
import com.aagu.blog.Views.CommentVO;
import com.aagu.blog.Views.LabelManageVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.aagu.blog.Common.Const.COMMENT_PAGE_LEN;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    private final FrontService frontService;

    private static final String DATA = "data";

    public AdminController(AdminService adminService, FrontService frontService) {
        this.adminService = adminService;
        this.frontService = frontService;
    }

    /**
     * admin主页
     * @return admin主页
     */
    @GetMapping(value = "")
    public String admin() {
        return "admin/admin";
    }

    /**
     * 登录页面
     * @return 登录页
     */
    @GetMapping(value = "/login")
    public String login() {
        return "admin/login";
    }

    /**
     * 登录操作
     * @param name 用户名
     * @param pwd 密码
     * @param attributes 登录信息
     * @param session HttpSession
     * @return 管理员主页面或登录失败提示
     */
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

    /**
     * 后台管理主页面
     * @param page 分页
     * @param model ThymeLeaf Model
     * @return 后台管理主页
     */
    @GetMapping(value = "/main")
    public String mainTem(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model) {
        model.addAttribute(DATA, adminService.getMainAdminPage(page));
        return "admin/main-tem";
    }

    //@GetMapping(value = "/resources")
    /*public String resourceTem(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model) {
        //model.addAttribute("data", null);
        return "admin/resource-tem";
    }*/

    /**
     * 文章管理页面
     * @param labelId 标签ID
     * @param page 分页
     * @param search 关键词
     * @param model ThymeLeaf Model
     * @param request HttpRequest
     * @return 文章管理页
     */
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
     * 获取标签数据
     * @return 标签集合
     */
    @ResponseBody
    @RequestMapping(value = "/labels-data", headers = {"content-Type=application/json"})
    public Set<LabelManageVO> getLabelsData() {
        ServerResponse<Set<LabelManageVO>> response = adminService.getTreeViewData();
        return response.getData();
    }

    /**
     * 写文章页面
     * @param id 文章ID
     * @param model ThymeLeaf Model
     * @return 写文章页
     */
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

    /**
     * 更新文章
     * @param article 新的文章
     * @return JSON对象
     */
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
        List<Comment> comments = adminService.getCommentByPage(
                (page-1) * COMMENT_PAGE_LEN,
                page * COMMENT_PAGE_LEN - 1,
                null);
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

    /**
     * 添加标签
     * @param name 标签名
     * @param parentId 父标签ID
     * @return JSON对象
     */
    @PutMapping(value = "add-label")
    @ResponseBody
    public ServerResponse addLabel(@RequestParam(value = "name") String name,
                                   @RequestParam(value = "parentId", defaultValue = "0") Integer parentId) {
        if (name.isEmpty()) return ServerResponse.createErrorMessage("标签名不能为空");
        return adminService.addLabel(name, parentId);
    }

    /**
     * 更新标签
     * @param name 标签名
     * @param parentId 父标签ID
     * @param id 标签ID
     * @return JSON对象
     */
    @PutMapping(value = "update-label")
    @ResponseBody
    public ServerResponse updateLabel(@RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "parentId", required = false) Integer parentId,
                                      @RequestParam(value = "id") Integer id) {
        if (name != null) {
            return adminService.updateLabelName(name, id);
        }
        if (parentId != null) {
            return adminService.updateParentLabel(parentId, id);
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

    /**
     * 删除标签
     * @param id 标签ID
     * @return JSON对象
     */
    @DeleteMapping(value = "delete-label")
    @ResponseBody
    public ServerResponse deleteLabel(@RequestParam(value = "id") Integer id) {
        if (id < 1) return ServerResponse.createErrorMessage("没有这个标签");
        return adminService.deleteLabel(id);
    }
}
