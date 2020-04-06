package com.aagu.blog.view;

public class ArchiveLabel {
    private String text;
    private String to;

    public ArchiveLabel(String text, String to) {
        this.text = text;
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
