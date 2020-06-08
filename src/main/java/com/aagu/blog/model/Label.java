package com.aagu.blog.model;

import java.io.Serializable;

public class Label implements Serializable {
    private Integer id;
    private Integer parentId;
    private String name;

    public Label(Integer id, Integer parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public Label(Integer parentId, String name) {
        this.parentId = parentId;
        this.name = name;
    }

    // made for deserializer
    public Label() {
        this(-1, "null");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Label{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Label label = (Label) o;

        if (!id.equals(label.id)) return false;

        if (!parentId.equals(label.parentId)) return false;

        return name.equals(label.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + parentId.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
