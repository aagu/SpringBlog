package com.aagu.blog.Views;

import java.util.Collection;

public class LabelManageVO {
    private String text;
    private Collection<String> tags;
    private Collection<LabelManageVO> nodes;
    private Integer parentId;
    private String parentName;
    private Integer id;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Collection<String> getTags() {
        return tags;
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }

    public Collection<LabelManageVO> getNodes() {
        return nodes;
    }

    public void setNodes(Collection<LabelManageVO> nodes) {
        this.nodes = nodes;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = result * 31 + parentId.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LabelManageVO that = (LabelManageVO) o;
        if (!text.equals(that.text)) return false;
        if (!tags.equals(that.tags)) return false;
        return nodes.equals(that.nodes);
    }

    @Override
    public String toString() {
        return "LabelManageVO{" +
                "text='" + text + '\'' +
                ", tags=" + tags +
                ", nodes=" + nodes +
                ", parentId=" + parentId +
                ", parentName='" + parentName + '\'' +
                ", id=" + id +
                '}';
    }
}
