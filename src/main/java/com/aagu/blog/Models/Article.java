package com.aagu.blog.Models;

public class Article {
    private Integer id;
    private String link;
    private String date;
    private Integer labelId;
    private String title;
    private String detail;

    public Article() {
    }

    public Article(String link, String date, Integer labelId, String detail) {
        this.link = link;
        this.date = date;
        this.labelId = labelId;
        this.detail = detail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        return id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", link='" + link + '\'' +
                ", date='" + date + '\'' +
                ", labelId=" + labelId +
                ", detail='" + detail + '\'' +
                '}';
    }
}
