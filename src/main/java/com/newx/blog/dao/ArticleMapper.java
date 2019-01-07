package com.newx.blog.dao;

import com.newx.blog.entity.ArticleEntity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by home on 2017/9/21.
 */
public interface ArticleMapper {
    List<HashMap<String, Object>> selectAll();
    HashMap<String, Object> getArticle(int articleID);
    int updateViewNum(int articleID);
    int createArticle(ArticleEntity articleEntity);
    List<HashMap<String, Object>> getArchive();

    HashMap<String, Object> getPrev(int articleId);
    HashMap<String, Object> getNext(int articleId);
}
