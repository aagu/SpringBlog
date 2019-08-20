package com.aagu.blog.Models;

import java.io.Serializable;

public class Comment implements Serializable {
    private Integer id;
    private String detail;
    private String email;
    private String articleTitle;
    private Integer articleId;
    private String status;
    private String date;

    public Comment() {
    }

    public Comment(String detail, String email, String articleTitle) {
        this.detail = detail;
        this.email = email;
        this.articleTitle = articleTitle;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", detail='" + detail + '\'' +
                ", email='" + email + '\'' +
                ", articleTitle='" + articleTitle + '\'' +
                ", articleId=" + articleId +
                ", status='" + status + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
