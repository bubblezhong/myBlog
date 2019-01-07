package com.newx.blog.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

/**
 * Created by home on 2017/9/30.
 */
public class ImageCrop {

    private static String DEFAULT_CUT_PREVFIX = "cut_";

    /**
     * Description: 根据原图与裁切size截取局部图片
     * @param srcImg 源图片
     * @param output 图片输出流
     * @param rect 需要截取部分的坐标和大小
     */
    // 4
    public void cutImage(File srcImg, OutputStream output, java.awt.Rectangle rect) {
        if (srcImg.exists()) {
            java.io.FileInputStream fis = null;
            ImageInputStream iis = null;
            try {
                fis = new FileInputStream(srcImg);
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG,
                // JPEG, WBMP, GIF, gif]
                String types = Arrays.toString(ImageIO.getReaderFormatNames())
                        .replace("]", ",");
                String suffix = null;
                // 获取图片后缀
                if (srcImg.getName().indexOf(".") > -1) {
                    suffix = srcImg.getName().substring(srcImg.getName().lastIndexOf(".") + 1);
                }// 类型和图片后缀全部小写，然后判断后缀是否合法
                if (suffix == null
                        || types.toLowerCase().indexOf(suffix.toLowerCase() + ",") < 0) {
                    return;
                }
                // 将FileInputStream 转换为ImageInputStream
                iis = ImageIO.createImageInputStream(fis);
                // 根据图片类型获取该种类型的ImageReader
                ImageReader reader = ImageIO.getImageReadersBySuffix(suffix).next();
                reader.setInput(iis, true);
                ImageReadParam param = reader.getDefaultReadParam();
                param.setSourceRegion(rect);
                BufferedImage bi = reader.read(0, param);
                ImageIO.write(bi, suffix, output);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null)
                        fis.close();
                    if (iis != null)
                        iis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //生成目标文件路径
    // 3
    public void cutImage(File srcImg, String destImgPath,java.awt.Rectangle rect) {
        File destImg = new File(destImgPath);
        if (destImg.exists()) {
            String p = destImg.getPath();
            try {
                if (!destImg.isDirectory())
                    p = destImg.getParent();
                if (!p.endsWith(File.separator))
                    p = p + File.separator;
                cutImage(srcImg,new java.io.FileOutputStream(p + DEFAULT_CUT_PREVFIX+ "_"+ srcImg.getName()), rect);
            } catch (FileNotFoundException e) {
            }
        }
    }


    // 2
    public void cutImage(String srcImg, String destImg, int x, int y, int width,
                         int height) {
        cutImage(new File(srcImg), destImg, new java.awt.Rectangle(x, y, width, height));
    }

//    public void cutImageTest() throws IOException {
//        File srcImg = new File("C:\\Users\\home\\Downloads\\ti038a5003.jpg");
//        String suffix = null;
//        // 获取图片后缀
//        if (srcImg.getName().indexOf(".") > -1) {
//            suffix = srcImg.getName().substring(srcImg.getName().lastIndexOf(".") + 1);
//        }
//        FileInputStream fin = new FileInputStream(srcImg);
//        BufferedImage originalImgage  = ImageIO.read(fin);
//        BufferedImage subImage = originalImgage.getSubimage(0,0,1000,1500);
//        ImageIO.write(subImage, suffix, new File("C:\\Users\\home\\cut.jpg"));
//    }
    // 1
    public static void main(String[] args) throws IOException {
        new ImageCrop().cutImage("C:/Users/cm/Desktop/我的页面/images/boyNoImg.jpg", "C:/Users/cm/Desktop/我的页面/images/imgs",0, 0, 61, 166);
//        new ImageCrop().cutImageTest();
        //new ImageUtil2().cutImage("C:/Users/cm/Desktop/Jcrop-master/demos/demo_files/sago.jpg", "C:/Users/cm/Desktop/我的页面/images/imgs",124, 110, 196, 176);
    }
}
