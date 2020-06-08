package com.aagu.blog.view;

import com.aagu.blog.model.Article;
import com.aagu.blog.model.Label;

public class ArticleDetailVO {
    private Article article;
    private Label label;
    private Integer prev;
    private Integer next;

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
}
