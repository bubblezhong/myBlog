package com.newx.blog.entity;

/**
 * Created by home on 2017/9/20.
 */
public class TagEntity {
    private int tagId;
    private String tagName;
    private int remove;

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getRemove() {
        return remove;
    }

    public void setRemove(int remove) {
        this.remove = remove;
    }
}
