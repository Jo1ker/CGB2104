package com.jt.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PageResult {
    private String query;       //查询的key
    private Integer pageNum;    //页数
    private Integer pageSize;   //
    private Long total;
    private Object rows;


}
