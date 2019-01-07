package com.newx.blog.controller;

import com.newx.blog.service.ArticleService;
import com.newx.blog.service.OSSImageService;
import com.newx.blog.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by home on 2017/9/21.
 */
@RequestMapping(value = "/api")
@RestController
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    OSSImageService ossImageService;

    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.GET)
    public ServerResponse getArticle(@PathVariable int articleId) {
        return articleService.getArticle(articleId);
    }
    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public ServerResponse getArticleList(int page, int pageSize) {
        return articleService.getArticleList(page,pageSize);
    }

    @RequestMapping(value = "/archive", method = RequestMethod.GET)
    public ServerResponse getArchive() {
        return articleService.getArchive();
    }

    @RequestMapping(value = "/article", method = RequestMethod.POST) //注解
    public ServerResponse postArticles(String title, String descr, String content, int categoryId,String tagString, String cover) {
        return articleService.createArticle(title,descr, content, categoryId,tagString, cover);
    }
    @RequestMapping(value = "/uploadArticlePic", method = RequestMethod.POST)
    public ServerResponse uploadArticlePic(MultipartFile file) throws Exception {
        return ServerResponse.createBySuccess(ossImageService.uploadArticlePic(file));
    }
    @RequestMapping(value = "/uploadCover", method = RequestMethod.POST)
    public ServerResponse uploadCover(MultipartFile file, int x, int y, int w,int h) throws Exception {
        return ServerResponse.createBySuccess(ossImageService.uploadCover(file,x,y,w,h));
    }
}
