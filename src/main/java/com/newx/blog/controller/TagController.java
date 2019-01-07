package com.newx.blog.controller;

import com.newx.blog.service.TagService;
import com.newx.blog.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * Created by home on 2017/9/20.
 */
@RestController
@RequestMapping(value = "/api")
public class TagController {
    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    public ServerResponse<List<HashMap<String, Object>>> getTags() {
        return tagService.getAll();
    }

    //  /api/tags1 => id=1
    @RequestMapping(value = "/tag1", method = RequestMethod.GET)
    public ServerResponse getTag1() {
        return tagService.getTag1();
    }

    @RequestMapping(value = "/tags/{tagId}", method = RequestMethod.GET)
    public ServerResponse<List<HashMap<String, Object>>> getArticlesByTag(@PathVariable int tagId) {
        return tagService.getArticlesByTag(tagId);
    }
    @RequestMapping(value = "/tags", method = RequestMethod.POST)
    public ServerResponse createTag(String tagName) {
        return tagService.createTag(tagName);
    }
}
