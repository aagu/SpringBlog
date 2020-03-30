package com.aagu.blog.Views;

import com.aagu.blog.Models.Article;
import com.aagu.blog.Models.Comment;
import com.aagu.blog.Models.Label;
import com.aagu.blog.Utils.MarkdownParser;
import com.aagu.blog.Utils.TextUtil;

import java.util.List;

public class ArticleDetailVO {
    private Article article;
    private Label label;
    private Integer prev;
    private Integer next;
    private List<Label> allLabels;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
//        article.setContent(MarkdownParser.INSTANCE.parser(article.getContent()));
        this.article = article;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Integer getPrev() {
        return prev;
    }

    public void setPrev(Integer prev) {
        this.prev = prev;
    }

    public Integer getNext() {
        return next;
    }

    public void setNext(Integer next) {
        this.next = next;
    }


    public List<Label> getAllLabels() {
        return allLabels;
    }

    public void setAllLabels(List<Label> allLabels) {
        this.allLabels = allLabels;
    }
}
