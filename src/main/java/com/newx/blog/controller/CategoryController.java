package com.newx.blog.controller;

import com.newx.blog.dao.CategoryMapper;
import com.newx.blog.service.CategoryService;
import com.newx.blog.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by home on 2017/9/25.
 */
@RestController
@RequestMapping(value = "/api")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/category")
    public ServerResponse getCategories() {
        return categoryService.getCategories();
    }
//    @RequestMapping(value = "/category/{categoryId}")
//    public ServerResponse getCategories(@PathVariable int categoryId) {
//        return categoryService.getArticlesByCategoryId(categoryId);
//    }
}
