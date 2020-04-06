package com.aagu.blog.view;

import com.aagu.blog.Models.Comment;

import java.util.List;

public class CommentVO {
    private List<Comment> comments;
    private Integer currePage;
    private Integer totalPage;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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
