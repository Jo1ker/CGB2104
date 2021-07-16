package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ImageVO {
    private String virtualPath;  //磁盘路径
    private String urlPath;      //虚拟地址
    private String fileName;     //文件名称
}
