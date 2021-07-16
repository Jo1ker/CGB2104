package com.jt.service;

import com.jt.vo.ImageVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Service
@PropertySource("classpath:/image.properties")//动态导入pro配置文件
public class FileServiceImpl implements FileService{
    //最优做法：应该使用配置文件动态的属性赋值
    @Value("${file.localDirPath}")
    private String localDirPath;     //= "F:/images";
    @Value("${file.preURLPath}")
    private String preURLPath;       // = "http://image.jt.com";

    /**
     * 1.验证上传的文件是图片 jpg/png/gif 采用正则的方式校验
     * 2.防止恶意程序攻击，验证图片是否有宽度和高度
     * 3.文件分目录存储 例如：/2021/11/11
     *               例如2：hash码  8位hash  xx/xx/xx/xx
     * 数据hash时，因为是算法，可能造成数据分配不均
     * 4.防止文件重名，修改文件名称 UUID
     * */
    @Override
    public ImageVO upload(MultipartFile file) {
        //1.校验图片类型是否正确  正则表达式
        //1.1 获取文件名称
        String fileName = file.getOriginalFilename();
        fileName = fileName.toLowerCase();
        //1.2 正则校验
        if (!fileName.matches("^.+\\.(jpg|png|gif)$")){
            //如果文件不是图片 则返回null
            return null;
        }
        //2.校验文件是否为恶意程序
        //2.1将文件转化为图片对象 通过图片对象 获取宽度和高度
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            int height = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();
            if (height == 0 || width == 0){
                return null;
            }

            //3实现分目录存储
            //3.1按照时间分配目录 /yyyy/MM/dd/
            String dateDirPath = new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
            //3.2 "F:/images/yyyy/MM/dd/"
            String localDir = localDirPath + dateDirPath;
            File dirFile = new File(localDir);
            if (!dirFile.exists()){//如果目录不存在，则创建目录
                dirFile.mkdirs();
            }

            //4.防止文件重名 生成UUID
            String uuid = UUID.randomUUID().toString()
                    .replace("-", "");
            int index = fileName.lastIndexOf(".");
            //获取后缀   .jpg
            String fileType = fileName.substring(index);
            String realFilename = uuid+fileType;

            //5.实现文件上传
            //5.1 拼接文件全路径 目录/文件名称
            String localFilePath = localDir + realFilename;
            //5.2 完成文件上传
            file.transferTo(new File(localFilePath));

            /**
             * 不带磁盘的本地存储路径!!!
             * F:\images\2021\07\13\18e71866e39a4a99aaafa8860e240532.jpg
             * */
            String virtualPath = dateDirPath + realFilename;
            //图片访问网络地址：http://image.jt.com/2021/07/13/18e71866e39a4a99aaafa8860e240532.jpg
            //真实的磁盘地址   F:/images/2021/07/13/18e71866e39a4a99aaafa8860e240532.jpg
            String urlPath = preURLPath + virtualPath;
            ImageVO imageVO =  new ImageVO(virtualPath,urlPath,realFilename);
            System.out.println(imageVO);
            return imageVO;
        } catch (IOException e) {
            e.printStackTrace();
            //终止程序
            return null;
        }

    }

    //删除文件 1.准备文件全路径 2.执行删除操作
    @Override
    public void deleteFile(String virtualPath) {
        //  F:/images/yyyy/MM/dd/uuid.jpg
        String path = localDirPath + virtualPath;
        File file = new File(path);
        //实现文件删除操作
        file.delete();
    }

    /**
     * 步骤：
     *      1.准备文件上传的目录
     *      2.获取文件名称
     *      3.拼接文件目录
     *      4.实现文件上传
     *    关于业务层异常处理原则：将检查异常，转化为运行时异常
     * */
//    @Override
//    public void upload(MultipartFile file) {
//        String filePath = "F:/images/";
//        File fileDir = new File(filePath);
//        //判断目录是否存在
//        if (!fileDir.exists()){
//            //fileDir.mkdir();//创建一级目录
//            fileDir.mkdirs();//创建多级目录
//        }
//        //获取文件名称
//        String fileName = file.getOriginalFilename();
//        //文件上传目录路径
//        String path = filePath + fileName;
//        //实现文件上传
//        try {
//            file.transferTo(new File(path));
//        } catch (IOException e) {
//            //将检查异常，转化为运行时异常
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }


}
