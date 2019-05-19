package com.aagu.blog.Views;

public class CommentVO {
    private String detail;
    private String email;
    private String articleTitle;
    private Integer commentId;
    private Integer articleId;

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

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "CommentVO{" +
                "detail='" + detail + '\'' +
                ", email='" + email + '\'' +
                ", articleTitle='" + articleTitle + '\'' +
                ", commentId=" + commentId +
                ", articleId=" + articleId +
                '}';
    }
}
