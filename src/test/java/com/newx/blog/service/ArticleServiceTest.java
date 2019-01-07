package com.newx.blog.service;

import com.newx.blog.BlogApplicationTests;
import com.newx.blog.dao.ArticleMapper;
import com.newx.blog.util.ServerResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by home on 2017/9/22.
 */
public class ArticleServiceTest extends BlogApplicationTests{
    @Autowired
    ArticleMapper articleMapper;

    @Test
    public void testGetArticle() throws Exception {
       String url = "http://oss.c";
        String[] u = url.split(":");
        String Nurl = "https:" + u[1];
        System.out.println(Nurl);
        System.out.println(Nurl);
        System.out.println(Nurl);
    }
}