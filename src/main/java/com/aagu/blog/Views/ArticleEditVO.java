package com.aagu.blog.Views;

import com.aagu.blog.Models.Label;

import java.util.List;

public class ArticleEditVO {
    private Integer id;
    private String detail;
    private String title;
    private Integer labelId;
    private List<Label> labels;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        return "ArticleEditVO{" +
                "id=" + id +
                ", detail='" + detail + '\'' +
                ", title='" + title + '\'' +
                ", labelId=" + labelId +
                ", labels=" + labels +
                '}';
    }
}
