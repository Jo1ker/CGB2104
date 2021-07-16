package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


@TableName("rights") //windows系统 字母大小写 不敏感 Linux系统 严格区分大小写
@Data
@Accessors(chain = true)
public class Rights extends BasePojo{
    private Integer id;
    private String name;
    private Integer parentId;
    private String path;
    private Integer level;
    @TableField(exist = false)
    private List<Rights> children; //不是表格固有属性
}
