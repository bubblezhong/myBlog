package com.newx.blog.dao;

import java.util.HashMap;
import java.util.List;

/**
 * Created by home on 2017/9/25.
 */
public interface CategoryMapper {
    List<HashMap<String, Object>> getCategories();
    List<HashMap<String, Object>> getArticlesByCategoryId(int categoryId);
}
