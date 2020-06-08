package com.aagu.blog.controller;

import com.aagu.blog.exception.NotFoundException;
import com.aagu.blog.service.ArticleService;
import com.aagu.blog.service.FrontService;
import com.aagu.blog.util.HttpUtil;
import com.aagu.blog.util.TextUtil;
import com.aagu.blog.view.BlogVO;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("")
public class FrontRestController {
    private final FrontService frontService;
    private final ArticleService articleService;

    public FrontRestController(FrontService frontService, ArticleService articleService) {
        this.frontService = frontService;
        this.articleService = articleService;
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
                       @RequestParam(value = "archive", required = false) String archive,
                       @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                       @RequestParam(value = "search", required = false) String keyWord) {
        Map<String, String> params = new HashMap<>();
        params.put("status", "published");
        if (label != null && label > 0) {
            params.put("labelId", String.valueOf(label));
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
    public Object detail(@PathVariable(value = "index", required = false) Integer index) {
        try {
            return articleService.getViewDetail(index);
        } catch (NotFoundException e) {
            return HttpUtil.createResponse(40400, "article with id " + index + " not found", null);
        }
    }
}
