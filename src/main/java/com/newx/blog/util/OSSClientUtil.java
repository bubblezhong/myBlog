package com.newx.blog.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Random;

public class OSSClientUtil {

    // endpoint以杭州为例，其它region请按实际情况填写
    private String endpoint = "oss-cn-shanghai.aliyuncs.com";
    // accessKey
    private String accessKeyId = "LTAIw9QfPaWovM1p";
    private String accessKeySecret = "JsGAD1Qx1yAj3wwB9JdizImzP2Fek9";
    //空间
    private String bucketName = "zxx-blog";
    //文件存储目录
    private String filedir = "images/";

    private OSSClient ossClient;

    public OSSClientUtil() {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 初始化
     */
    public void init() {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 销毁
     */
    public void destory() {
        ossClient.shutdown();
    }

    /**
     * 上传图片
     *
     * @param url
     */
    public void uploadImg2Oss(String url) throws Exception {
        File fileOnServer = new File(url);
        FileInputStream fin;
        try {
            fin = new FileInputStream(fileOnServer);
            String[] split = url.split("/");
            this.uploadFile2OSS(fin, split[split.length - 1]);
        } catch (FileNotFoundException e) {
            throw new Exception("图片上传失败");
        }
    }


    public String uploadImg2Oss(MultipartFile file) throws Exception {
        if (file.getSize() > 1024 * 1024 * 10) {
            throw new Exception("上传图片大小不能超过1M！");
        }
        String originalFilename = file.getOriginalFilename();
        System.out.println(originalFilename);
        String substring = originalFilename.toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
        try {
            InputStream inputStream = file.getInputStream();
            this.uploadFile2OSS(inputStream, name);
            return name;
        } catch (Exception e) {
            throw new Exception("图片上传失败");
        }
    }
    public String uploadCover(MultipartFile srcImg, int x,int y, int w,int h) throws Exception {
        String suffix = null;
        // 获取图片后缀
        if (srcImg.getOriginalFilename().indexOf(".") > -1) {
            suffix = srcImg.getOriginalFilename().substring(srcImg.getOriginalFilename().lastIndexOf(".") + 1);
        }

        InputStream fin = srcImg.getInputStream();
        BufferedImage originalImgage  = ImageIO.read(fin);
        BufferedImage subImage = originalImgage.getSubimage(x,y,w,h);
        String fileName = "temp."+suffix;
        ImageIO.write(subImage, suffix, new File(fileName));

        File resultFile = new File(fileName);
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + fileName;
        try {
            InputStream inputStream = new FileInputStream(resultFile);
            this.uploadFile2OSS(inputStream, name);
            return name;
        } catch (Exception e) {
            throw new Exception("图片上传失败");
        }
    }

    /**
     * 上传到OSS服务器  如果同名文件会覆盖服务器上的
     *
     * @param instream 文件流
     * @param fileName 文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public String uploadFile2OSS(InputStream instream, String fileName) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
            PutObjectResult putResult = ossClient.putObject(bucketName, filedir + fileName, instream, objectMetadata);
            ret = putResult.getETag();

        } catch (IOException e) {
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 获得图片路径
     *
     * @param fileUrl
     * @return
     */
    public String getImgUrl(String fileUrl) {
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            return this.getUrl(this.filedir + split[split.length - 1]);
        }
        return null;
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param FilenameExtension 文件后缀
     * @return String
     */
    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase("jpeg") ||
                FilenameExtension.equalsIgnoreCase("jpg") ||
                FilenameExtension.equalsIgnoreCase("png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase("html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase("pptx") ||
                FilenameExtension.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase("docx") ||
                FilenameExtension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            String httpsUrl = "https:" + url.toString().split(":")[1];
            return httpsUrl;
        }
        return null;
    }

//    public File cutImage(MultipartFile srcImg, int x,int y, int w,int h) throws IOException {
//////        File srcImg = new File("C:\\Users\\home\\Downloads\\ti038a5003.jpg");
////        String suffix = null;
////        // 获取图片后缀
////        if (srcImg.getOriginalFilename().indexOf(".") > -1) {
////            suffix = srcImg.getOriginalFilename().substring(srcImg.getOriginalFilename().lastIndexOf(".") + 1);
////        }
////        InputStream fin = srcImg.getInputStream();
////        BufferedImage originalImgage  = ImageIO.read(fin);
////        BufferedImage subImage = originalImgage.getSubimage(x,y,w,h);
////        ImageIO.write(subImage, suffix, new File("temp."+suffix));
////        return new File("temp."+suffix);
//    }
}