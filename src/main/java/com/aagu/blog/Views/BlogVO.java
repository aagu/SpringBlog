package com.aagu.blog.Views;

import com.aagu.blog.Models.Article;
import com.aagu.blog.Models.Label;

import java.util.List;

public class BlogVO {
    List<Article> articles;
    List<Label> labels;
    Integer pages;

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
}
