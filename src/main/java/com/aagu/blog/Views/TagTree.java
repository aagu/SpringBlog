package com.aagu.blog.Views;

import java.util.Collection;

public class TagTree {
    private String label;
    private Collection<TagTree> children;
    private Integer parentId;
    private String parentName;
    private Integer id;

    public TagTree() {

    }

    public TagTree(Integer id, Integer parentId, String label) {
        this.id = id;
        this.parentId = parentId;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Collection<TagTree> getChildren() {
        return children;
    }

    public void setChildren(Collection<TagTree> children) {
        this.children = children;
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

        TagTree that = (TagTree) o;
        if (!label.equals(that.label)) return false;
        return children.equals(that.children);
    }

    @Override
    public String toString() {
        return "TagTree{" +
                "label='" + label + '\'' +
                ", children=" + children +
                ", parentId=" + parentId +
                ", parentName='" + parentName + '\'' +
                ", id=" + id +
                '}';
    }
}
