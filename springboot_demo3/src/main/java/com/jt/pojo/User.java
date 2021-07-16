package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("demo_user")//实现对象与表名的映射
public class User {
    //设定主键自增
    @TableId(type = IdType.AUTO)
    private Integer id;
    //@TableField("name")//实现属性与字段的映射
    private String name;
    private Integer age;
    private String sex;
}
