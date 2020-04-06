package com.aagu.blog.view;

import java.util.Collection;

public class TagTree {
    private String name;
    private Collection<TagTree> children;
    private Integer parentId;
    private String parentName;
    private Integer id;

    public TagTree() {

    }

    public TagTree(Integer id, Integer parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!name.equals(that.name)) return false;
        return children.equals(that.children);
    }

    @Override
    public String toString() {
        return "TagTree{" +
                "label='" + name + '\'' +
                ", children=" + children +
                ", parentId=" + parentId +
                ", parentName='" + parentName + '\'' +
                ", id=" + id +
                '}';
    }
}
