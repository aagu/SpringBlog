package com.aagu.blog.view;

import com.aagu.blog.Models.Article;
import com.aagu.blog.Models.Label;

import java.util.Collection;
import java.util.List;

public class ArticleManageVO {
    private Collection<Label> labels;
    private List<Article> articles;
    private String requestUrl;
    private Integer currentLabelsId;
    private Integer currePage;
    private Integer totalPage;

    public Collection<Label> getLabels() {
        return labels;
    }

    public void setLabels(Collection<Label> labels) {
        this.labels = labels;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Integer getCurrentLabelsId() {
        return currentLabelsId;
    }

    public void setCurrentLabelsId(Integer currentLabelsId) {
        this.currentLabelsId = currentLabelsId;
    }

    public Integer getCurrePage() {
        return currePage;
    }

    public void setCurrePage(Integer currePage) {
        this.currePage = currePage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
