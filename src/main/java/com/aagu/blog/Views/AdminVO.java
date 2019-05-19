package com.aagu.blog.Views;

import com.aagu.blog.Models.Article;
import com.aagu.blog.Models.Comment;

import java.util.List;

public class AdminVO {
    private List<Article> articles;
    private List<CommentVO> comments;
    private Integer peopleCount;
    private Integer articleCount;
    private Integer infoCount;

    public AdminVO() {
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<CommentVO> getComments() {
        return comments;
    }

    public void setComments(List<CommentVO> comments) {
        this.comments = comments;
    }

    public Integer getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(Integer peopleCount) {
        this.peopleCount = peopleCount;
    }

    public Integer getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }

    public Integer getInfoCount() {
        return infoCount;
    }

    public void setInfoCount(Integer infoCount) {
        this.infoCount = infoCount;
    }

    @Override
    public String toString() {
        return "AdminVO{" +
                "comments=" + comments +
                ", contentList=" + articles +
                ", peopleCount=" + peopleCount +
                '}';
    }
}
