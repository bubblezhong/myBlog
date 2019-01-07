package com.newx.blog.controller;

import com.github.pagehelper.PageInfo;
import com.newx.blog.service.ArticleService;
import com.newx.blog.service.CategoryService;
import com.newx.blog.service.TagService;
import com.newx.blog.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by home on 2017/11/14.
 */
@Controller
public class PageController {
    @Autowired
    ArticleService articleService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;

    // 首页
    @RequestMapping(value="/")
    String home(@RequestParam(defaultValue = "1") int page, Model model) {
        // 文章列表
        PageInfo pageInfo = (PageInfo) articleService.getArticleList(page, 9).getData();
        List<HashMap<String, Object>> articleList =  pageInfo.getList();

        model.addAttribute("title", "浅书 - AI学习笔记");
        model.addAttribute("keywords", "浅书,AI浅书,爱浅书,AI学习笔记,郑心享,笔记,编程,博客,IT学习,python,人工智能,大数据,深度学习,爬虫");
        model.addAttribute("description", "浅书 - AI学习笔记，是我的个人技术博客，目前侧重于人工智能领域的学习，在这里记录和分享自己的学习点滴，提升自我，分享自我。");
        model.addAttribute("articleList", articleList);

        // 分类列表
        List<HashMap<String, Object>> categories = (List<HashMap<String, Object>>) categoryService.getCategories().getData();
        model.addAttribute("categories", categories);

        // 标签列表
        List<HashMap<String, Object>> tags = (List<HashMap<String, Object>>) tagService.getAll().getData();
        model.addAttribute("tags", tags);
        model.addAttribute("pages", pageInfo.getPages());
        model.addAttribute("pageNum", pageInfo.getPageNum());
        model.addAttribute("prevPage", pageInfo.getPrePage());
        model.addAttribute("nextPage", pageInfo.getNextPage());

        model.addAttribute("categoryId", -1);

        return "index";
    }

    // 文章
    @RequestMapping(value="/article/{articleId}")
    String article(@PathVariable int articleId, Model model) {

        HashMap<String, Object> article = (HashMap<String, Object>) articleService.getArticle(articleId).getData();
        model.addAttribute("title", article.get("title") + " |  小洋葱");
        model.addAttribute("cover", article.get("cover"));
        model.addAttribute("tagList", article.get("tags"));
        model.addAttribute("categoryName", article.get("categoryName"));
        model.addAttribute("createTime", article.get("createTime"));
        model.addAttribute("viewNum", article.get("viewNum"));
        model.addAttribute("categoryId", article.get("categoryId"));
        model.addAttribute("content", article.get("content"));




        List<HashMap<String, Object>> tags = (List<HashMap<String, Object>>)article.get("tags");
        String tagsString = "";
        for(int i = 0; i < tags.size(); i++){
            tagsString += tags.get(i).get("tagName") + ",";
        }
        System.out.print(article.get("categoryName").toString());
        model.addAttribute("keywords",tagsString);
        model.addAttribute("description", article.get("title"));



        HashMap<String, Object> empty = new HashMap();
        empty.put("title", "无相关文章");
        empty.put("articleId", -1);
        // 上一篇，下一篇
        model.addAttribute("prev", empty);
        model.addAttribute("next", empty);

        if (articleService.getPrev(articleId).getStatus() == 0){
            HashMap<String, Object> prev = (HashMap<String, Object>) articleService.getPrev(articleId).getData();
            model.addAttribute("prev", prev);
        }

        if (articleService.getNext(articleId).getStatus() == 0){
            HashMap<String, Object> next = (HashMap<String, Object>) articleService.getNext(articleId).getData();
            model.addAttribute("next", next);
        }

        return "detail";
    }

    // 分类
//    @RequestMapping(value="/category")
//    String catgory( Model model) {
//        model.addAttribute("title", "分类 | 浅书 - AI学习笔记");
//        model.addAttribute("keywords", "浅书,AI浅书,爱浅书,AI学习笔记,郑心享,笔记,编程,博客,IT学习,IT学习,python,人工智能,大数据,深度学习,爬虫");
//        model.addAttribute("description", "浅书 - AI学习笔记，是我的个人技术博客，目前侧重于人工智能领域的学习，在这里记录和分享自己的学习点滴，提升自我，分享自我。");
//
//
//        List<HashMap<String, Object>> categoryList = (List<HashMap<String, Object>>) categoryService.getCategories().getData();
//
//        int categoryId = (int) categoryList.get(0).get("categoryId");
//
//        List<HashMap<String, Object>> articleList = (List<HashMap<String, Object>>) categoryService.getArticlesByCategoryId(categoryId).getData();
//
//
//        model.addAttribute("categoryName", articleList.get(0).get("categoryName"));
//        model.addAttribute("categoryList", categoryList);
//        model.addAttribute("articleList", articleList);
//
//
//        // 标签列表
//        List<HashMap<String, Object>> tags = (List<HashMap<String, Object>>) tagService.getAll().getData();
//        model.addAttribute("tags", tags);
//
//
//        return "category";
//
//    }

    @RequestMapping(value="/category/{categoryId}")
    String catgory(@PathVariable int categoryId, @RequestParam(defaultValue = "1") int pageNum, Model model) {
        model.addAttribute("title", "分类 | 浅书 - AI学习笔记");
        model.addAttribute("keywords", "浅书,AI浅书,爱浅书,AI学习笔记,郑心享,笔记,编程,博客,IT学习,IT学习,python,人工智能,大数据,深度学习,爬虫");
        model.addAttribute("description", "浅书 - AI学习笔记，是我的个人技术博客，目前侧重于人工智能领域的学习，在这里记录和分享自己的学习点滴，提升自我，分享自我。");


        PageInfo pageInfo  = (PageInfo) categoryService.getArticlesByCategoryId(categoryId,  pageNum, 10).getData();
       // List<HashMap<String, Object>> categoryList = (List<HashMap<String, Object>>) categoryService.getCategories().getData();
        //PageInfo pageInfo = (PageInfo) articleService.getArticleList(page, 10).getData();
        List<HashMap<String, Object>> articleList =  pageInfo.getList();

        try {
            model.addAttribute("categoryName", articleList.get(0).get("categoryName"));
           // model.addAttribute("categoryList", categoryList);
            model.addAttribute("articleList", articleList);

            model.addAttribute("pages", pageInfo.getPages());
            model.addAttribute("pageNum", pageInfo.getPageNum());
            model.addAttribute("prevPage", pageInfo.getPrePage());
            model.addAttribute("nextPage", pageInfo.getNextPage());
        } catch (Exception e){
            model.addAttribute("categoryName", "");
            //model.addAttribute("categoryList", new ArrayList<>());
            model.addAttribute("articleList", new ArrayList<>());
        }

        model.addAttribute("categoryId", categoryId);


        // 标签列表
        List<HashMap<String, Object>> tags = (List<HashMap<String, Object>>) tagService.getAll().getData();
        model.addAttribute("tags", tags);

        return "category";
    }


    // 标签
    @RequestMapping(value="/tag")
    String tag( Model model) {
        model.addAttribute("title", "标签 | 浅书 - AI学习笔记");
        model.addAttribute("keywords", "浅书,AI浅书,爱浅书,AI学习笔记,郑心享,笔记,编程,博客,IT学习,IT学习,python,人工智能,大数据,深度学习,爬虫");
        model.addAttribute("description", "浅书 - AI学习笔记，是我的个人技术博客，目前侧重于人工智能领域的学习，在这里记录和分享自己的学习点滴，提升自我，分享自我。");


        List<HashMap<String, Object>> tagList = (List<HashMap<String, Object>>)tagService.getAll().getData();

        int tagId = (int) tagList.get(0).get("tagId");

        List<HashMap<String, Object>> articleList = (List<HashMap<String, Object>>) tagService.getArticlesByTag(tagId).getData();


        model.addAttribute("tagName", articleList.get(0).get("tagName"));
        model.addAttribute("tagList", tagList);
        model.addAttribute("articleList", articleList);

        // 分类列表
        List<HashMap<String, Object>> categories = (List<HashMap<String, Object>>) categoryService.getCategories().getData();
        model.addAttribute("categories", categories);

        return "tag";

    }

    @RequestMapping(value="/tag/{tagId}")
    String tag(@PathVariable int tagId, Model model) {
        model.addAttribute("title", "标签 | 浅书 - AI学习笔记");
        model.addAttribute("keywords", "浅书,AI浅书,爱浅书,AI学习笔记,郑心享,笔记,编程,博客,IT学习,IT学习,python,人工智能,大数据,深度学习,爬虫");
        model.addAttribute("description", "浅书 - AI学习笔记，是我的个人技术博客，目前侧重于人工智能领域的学习，在这里记录和分享自己的学习点滴，提升自我，分享自我。");



        List<HashMap<String, Object>> tagList = (List<HashMap<String, Object>>)tagService.getAll().getData();


        List<HashMap<String, Object>> articleList = (List<HashMap<String, Object>>) tagService.getArticlesByTag(tagId).getData();

        if (articleList.size() != 0){
            model.addAttribute("tagName", articleList.get(0).get("tagName"));
        } else {
            model.addAttribute("tagName", "暂时没有结果");
        }
        model.addAttribute("tagList", tagList);


        model.addAttribute("articleList", articleList);

        // 分类列表
        List<HashMap<String, Object>> categories = (List<HashMap<String, Object>>) categoryService.getCategories().getData();
        model.addAttribute("categories", categories);

        return "tag";
    }

    // 关于
    @RequestMapping("/about")
    String about(Model model) {
        model.addAttribute("title", "小洋葱");
        model.addAttribute("keywords", "浅书,AI浅书,爱浅书,AI学习笔记,郑心享,笔记,编程,博客,IT学习,IT学习,python,人工智能,大数据,深度学习,爬虫");
        model.addAttribute("description", "浅书 - AI学习笔记，是我的个人技术博客，目前侧重于人工智能领域的学习，在这里记录和分享自己的学习点滴，提升自我，分享自我。");

        return "about";
    }

    // react
    @RequestMapping("/react")
    String react(Model model) {
        model.addAttribute("title", "iShare");
        model.addAttribute("keywords", "浅书,AI浅书,爱浅书,AI学习笔记,郑心享,笔记,编程,博客,IT学习,IT学习,python,人工智能,大数据,深度学习,爬虫");
        model.addAttribute("description", "浅书 - AI学习笔记，是我的个人技术博客，目前侧重于人工智能领域的学习，在这里记录和分享自己的学习点滴，提升自我，分享自我。");

        return "react";
    }


}
