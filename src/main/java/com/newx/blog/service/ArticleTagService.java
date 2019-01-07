package com.newx.blog.service;

import com.newx.blog.dao.ArticleTagMapper;
import com.newx.blog.entity.ArticleTagEntity;
import com.newx.blog.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by home on 2017/9/25.
 */
@Service
public class ArticleTagService {
    @Autowired
    ArticleTagMapper articleTagMapper;

    public ServerResponse createArticleTag(int tagId, int articleId) {
        ArticleTagEntity articleTagEntity = new ArticleTagEntity();
        articleTagEntity.setArticleId(articleId);
        articleTagEntity.setTagId(tagId);
        articleTagEntity.setRemove(0);
        int result = articleTagMapper.createArticleTag(articleTagEntity);
        if (result != 0) {
            return ServerResponse.createBySuccessMessage("创建成功");
        }else {
            return ServerResponse.createByErrorMessage("创建失败");
        }
    }
}
