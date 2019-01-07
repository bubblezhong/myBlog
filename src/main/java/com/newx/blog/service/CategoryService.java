package com.newx.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newx.blog.dao.CategoryMapper;
import com.newx.blog.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by home on 2017/9/25.
 */
@Service
public class CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    public ServerResponse getCategories() {
        List<HashMap<String, Object>> result = categoryMapper.getCategories();
        if (result != null) {
            return ServerResponse.createBySuccess(result);
        }else {
            return ServerResponse.createByErrorMessage("查询失败");
        }
    }


    public ServerResponse getArticlesByCategoryId(int categoryId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<HashMap<String, Object>> result = categoryMapper.getArticlesByCategoryId(categoryId);

        if (result == null) {
            return ServerResponse.createByErrorMessage("查询失败");
        }
        PageInfo pageInfo = new PageInfo(result);
        return ServerResponse.createBySuccess(pageInfo);
//        return ServerResponse.createBySuccess(result);
    }
}
