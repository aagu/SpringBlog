package com.aagu.blog.controller;

import com.aagu.blog.Services.FrontService;
import com.aagu.blog.Utils.TextUtil;
import com.aagu.blog.Views.ArticleDetailVO;
import com.aagu.blog.Views.BlogVO;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("")
public class FrontRestController {
    private final FrontService frontService;

    public FrontRestController(FrontService frontService) {
        this.frontService = frontService;
    }

    @GetMapping("/archives")
    public Object archive() {
        Map<String, Object> resp = new HashMap<>();
        List<Object> list = new ArrayList<>();
        list.add(frontService.getArchiveLabels());
        list.add(frontService.getAllFinalLabels());
        resp.put("items", list);
        resp.put("total", list.size());
        return resp;
    }

    @GetMapping(value = "/blog")
    public BlogVO blog(@RequestParam(value = "label", required = false) Integer label,
                       @RequestParam(value = "archive", required = false) Date archive,
                       @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                       @RequestParam(value = "search", required = false) String keyWord) {
        Map<String, Object> params = new HashMap<>();
        params.put("status", "published");
        if (label != null && label > 0) {
            params.put("labelId", label);
        }
        if (archive != null) {
            params.put("month", archive);
        }
        if (TextUtil.notEmpty(keyWord)){
            params.put("search", keyWord);
        }
        return frontService.getArticlesPage(page, params);
    }

    @GetMapping(value = "/detail/{index}")
    @ResponseBody
    public ArticleDetailVO detail(@PathVariable(value = "index", required = false) Integer index) {
        if (index == null) {
            return null;
        }
        return frontService.getArticleDetail(index);
    }
}
