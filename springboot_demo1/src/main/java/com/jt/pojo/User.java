package com.jt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

//注解的作用：动态的生成get/set/toString....方法
@Data  //一般为属性赋值 get/set方法
@NoArgsConstructor //无参构造
@AllArgsConstructor //含参构造
@Accessors(chain = true)//开启链式加载（重写set()方法）
public class User {
    private Integer id;
    private String name;
    private Integer age;
    private String sex;

//    public User setId(Integer id) {
//        this.id = id;
//        return this;
//    }
//
//    public User setName(String name) {
//        this.name = name;
//        return this;
//    }
//
//    public User setAge(Integer age) {
//        this.age = age;
//        return this;
//    }
}
