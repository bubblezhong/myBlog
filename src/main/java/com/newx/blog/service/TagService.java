package com.newx.blog.service;

import com.newx.blog.dao.TagMapper;
import com.newx.blog.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by home on 2017/9/22.
 */
@Service
public class TagService {
    @Autowired
    TagMapper tagMapper;
    public ServerResponse getArticlesByTag(int tagId) {
        List<HashMap<String, Object>> result = tagMapper.getArticlesByTag(tagId);
        if (result == null) {
            return ServerResponse.createByErrorMessage("查询有误");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (HashMap tag : result) {
            String date = sdf.format((Date)tag.get("time"));
            tag.put("time", date);
        }
        return ServerResponse.createBySuccess(result);
    }
    public ServerResponse getAll() {
        List<HashMap<String, Object>> result = tagMapper.selectAll();
        if (result != null)
            return ServerResponse.createBySuccess(result);
        else
            return ServerResponse.createByErrorMessage("暂未查询到数据");
    }
    public ServerResponse getArticleTag(int articleId) {
        List<HashMap<String, Object>> result = tagMapper.getArticleTag(articleId);
        return ServerResponse.createBySuccess(result);
    }
    public ServerResponse createTag(String tagName) {
        int result = tagMapper.createTag(tagName);
        if (result == 0) {
            return ServerResponse.createByErrorMessage("插入失败");
        } else
            return ServerResponse.createBySuccessMessage("插入成功");
    }
    public ServerResponse getTag1() {
        HashMap<String, Object> result = tagMapper.getTag1();
        if (result != null) {
            return ServerResponse.createBySuccess(result);
        }
        return ServerResponse.createByErrorMessage("no");
    }
}
