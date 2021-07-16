package com.jt.controller;

import com.jt.service.FileService;
import com.jt.vo.ImageVO;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;
    /**
     * 请求路径： "http://localhost:8091/file/upload"
     * 请求参数： file(binary)
     * 返回值：SysResult(ImageVO)
     * 缓存流 一次性搬运多大的字节效率最高？ 1024
     * 注意事项： 关闭流文件
     * MultipartFile: SpringMVC 专门针对文件上传开发的API
     * */
    @PostMapping("/upload")
    public SysResult upload(MultipartFile file){
        ImageVO imageVO = fileService.upload(file);
        //如果文件上传失败 1.null 2，如果报错抛出异常
        if (imageVO == null){
            return SysResult.fail();
        }
        return SysResult.success(imageVO);
    }

    /**
     * 实现文件删除操作
     *  URL地址：/file/deleteFile
     *  参数：virtualPath 虚拟路径
     *  返回值：SysResult对象
     * */
    @DeleteMapping("/deleteFile")
    public SysResult deleteFile(String virtualPath){
        fileService.deleteFile(virtualPath);
        return SysResult.success();
    }
}
