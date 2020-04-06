package com.aagu.blog.view;

import com.aagu.blog.Models.Article;
import com.aagu.blog.Models.Label;

import java.util.List;

public class BlogVO {
    private List<Article> articles;
    private List<Label> labels;
    private Integer pages;
    private Integer currePage;
    private List<String> archiveLabel;
    private String curreLabel;
    private String curreKeyWord;
    private String param;

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

    public List<String> getArchiveLabel() {
        return archiveLabel;
    }

    public void setArchiveLabel(List<String> archiveLabel) {
        this.archiveLabel = archiveLabel;
    }

    public String getCurreLabel() {
        return curreLabel;
    }

    public void setCurreLabel(String curreLabel) {
        this.curreLabel = curreLabel;
    }

    public String getCurreKeyWord() {
        return curreKeyWord;
    }

    public void setCurreKeyWord(String curreKeyWord) {
        this.curreKeyWord = curreKeyWord;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
