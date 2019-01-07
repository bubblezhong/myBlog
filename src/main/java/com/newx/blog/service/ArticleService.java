package com.newx.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newx.blog.dao.ArticleMapper;
import com.newx.blog.dao.ArticleTagMapper;
import com.newx.blog.dao.TagMapper;
import com.newx.blog.entity.ArticleEntity;
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
public class ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    TagMapper tagMapper;
    @Autowired
    ArticleTagService articleTagService;

    public ServerResponse getArticle(int articleId) {
        articleMapper.updateViewNum(articleId);
        HashMap<String, Object> result = articleMapper.getArticle(articleId);
        if (result != null){
            result.put("tags", tagMapper.getArticleTag((Integer) result.get("articleId")));
            return ServerResponse.createBySuccess(result);
        }
        else
            return ServerResponse.createByErrorMessage("查询错误");
    }

    public ServerResponse getArticleList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<HashMap<String, Object>> result = articleMapper.selectAll();
        System.out.println("\n\n\n\n" + pageNum + " " + pageSize + " " + result.size() +"\n\n\n\n");
        if (result != null){
            for (HashMap hashMap : result) {
                System.out.println(hashMap.get("articleId"));
                hashMap.put("tags", tagMapper.getArticleTag((Integer) hashMap.get("articleId")));
            }
            PageInfo pageInfo = new PageInfo(result);
            return ServerResponse.createBySuccess(pageInfo);
        }
        else
            return ServerResponse.createByErrorMessage("查询错误");
    }

    public ServerResponse createArticle(String title,String descr, String content, int categoryId, String tagString, String cover) {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle(title);
        articleEntity.setDescr(descr);
        articleEntity.setContent(content);
        articleEntity.setCategoryId(categoryId);
        articleEntity.setCreateTime(new Date());
        articleEntity.setUpdateTime(new Date());
        articleEntity.setCover(cover);
        int result = articleMapper.createArticle(articleEntity); //  arrticleEntuty.setArticleID(id);
        if (result != 0){
            String[] tagsArr = tagString.split(",");
            for (String tagId : tagsArr) {
                articleTagService.createArticleTag(Integer.parseInt(tagId),articleEntity.getArticleId());
            }
//            System.out.println("\n\n\n"+ articleEntity.getArticleId());
            return ServerResponse.createBySuccessMessage("文章发布成功");
        }
        else
            return ServerResponse.createByErrorMessage("查询错误");
    }

    public ServerResponse getPrev(int articleId) {
        HashMap<String, Object> prev = articleMapper.getPrev(articleId);

        if (prev != null){
            return ServerResponse.createBySuccess(prev);
        } else {
            return ServerResponse.createByError();
        }
    }
    public ServerResponse getNext(int articleId) {
        HashMap<String, Object> next = articleMapper.getNext(articleId);

        if (next != null){
            return ServerResponse.createBySuccess(next);
        } else {
            return ServerResponse.createByError();
        }
    }

    public ServerResponse getArchive() {
        List<HashMap<String, Object>> result = articleMapper.getArchive();

        if (result != null){
            return ServerResponse.createBySuccess(result);
        }
        else
            return ServerResponse.createByErrorMessage("查询错误");
    }
}
