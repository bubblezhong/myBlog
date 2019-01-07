package com.newx.blog.service;

import com.newx.blog.util.OSSClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by home on 2017/9/27.
 */
@Service
public class OSSImageService {


    public String uploadCover(MultipartFile file, int x, int y, int w, int h) throws Exception {
        OSSClientUtil ossClientUtil = new OSSClientUtil();
        if (file == null || file.getSize() <= 0) {
            throw new Exception("不能为空");
        }
        String name = ossClientUtil.uploadCover(file, x, y, w, h);
        String imgUrl = ossClientUtil.getImgUrl(name);
//        userDao.updateHead(userId, imgUrl);//只是本地上传使用的

        ossClientUtil.destory();
        return imgUrl;
    }

    public String uploadArticlePic(MultipartFile file) throws Exception {
        OSSClientUtil ossClientUtil = new OSSClientUtil();
        if (file == null || file.getSize() <= 0) {
            throw new Exception("不能为空");
        }
        String name = ossClientUtil.uploadImg2Oss(file);
        String imgUrl = ossClientUtil.getImgUrl(name);
//        userDao.updateHead(userId, imgUrl);//只是本地上传使用的
        ossClientUtil.destory();
        return imgUrl;
    }
}
