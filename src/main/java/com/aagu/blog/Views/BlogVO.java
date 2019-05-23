package com.aagu.blog.Views;

import com.aagu.blog.Models.Article;
import com.aagu.blog.Models.Label;

import java.util.List;

public class BlogVO {
    private List<Article> articles;
    private List<Label> labels;
    private Integer pages;
    private Integer currePage;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getCurrePage() {
        return currePage;
    }

    public void setCurrePage(Integer currePage) {
        this.currePage = currePage;
    }
}
