package com.sun.demo.mybatis.entity;

import java.util.List;

/**
 * @auther zhsun5@iflytek.com
 * @date 2018/1/8
 */
public class Org {
    private int id;
    private String name;
    private int parentId;
    private List<Org> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<Org> getChildren() {
        return children;
    }

    public void setChildren(List<Org> children) {
        this.children = children;
    }
}
