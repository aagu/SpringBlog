package com.aagu.blog.Views;

import com.aagu.blog.Models.Resource;

import java.util.List;

public class ResourceVO {
    private Integer page;
    private List<Resource> resources;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}
