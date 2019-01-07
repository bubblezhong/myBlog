package com.newx.blog.entity;

/**
 * Created by home on 2017/9/25.
 */
public class ArticleTagEntity {
    private int articleId;
    private int tagId;
    private int remove;

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public int getRemove() {
        return remove;
    }

    public void setRemove(int remove) {
        this.remove = remove;
    }
}
