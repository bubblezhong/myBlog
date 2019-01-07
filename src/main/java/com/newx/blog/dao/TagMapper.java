package com.newx.blog.dao;

import com.newx.blog.entity.TagEntity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by home on 2017/9/20.
 */
public interface TagMapper {
    List<HashMap<String, Object>> getArticlesByTag(int tadId);
    List<HashMap<String, Object>> selectAll();
    List<HashMap<String, Object>> getArticleTag(int articleId);
    int createTag(String tagName);
    HashMap<String, Object> getTag1();
}
