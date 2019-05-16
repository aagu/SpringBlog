package com.aagu.blog.Models;

public class Comment {
    private Integer id;
    private String detail;
    private String email;
    private String commentTitle;
    private Integer articleId;

    public Comment() {
    }

    public Comment(String detail, String email, String commentTitle) {
        this.detail = detail;
        this.email = email;
        this.commentTitle = commentTitle;
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

    public String getCommentTitle() {
        return commentTitle;
    }

    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
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

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", detail='" + detail + '\'' +
                ", email='" + email + '\'' +
                ", commentTitle='" + commentTitle + '\'' +
                ", articleId=" + articleId +
                '}';
    }
}
