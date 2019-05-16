package com.aagu.blog.Controllers;

import com.aagu.blog.Services.AdminService;
import com.aagu.blog.Views.ArticleManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    private static final String DATA = "data";

    @GetMapping(value = "")
    public String admin() {
        return "admin/admin";
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
}
