package com.jt.controller;

import com.jt.pojo.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//历史: 后端进行页面跳转时一般使用Controller注解
//当下: 前后端分离, 页面不归后端管理,RestController
//     RestController = Controller + @ResponseBody
//面试题: @Controller 和 @RestController
//@Controller
//@ResponseBody
@RestController
//Spring指定配置文件加载,将数据加载到容器中
//注解的规则:如果注解中只有一个属性,并且名称为value时,则属性名称可以省略
//         如果注解的属性有多个,则不能省略
//@PropertySource 根据指定路径,加载配置文件,交给Spring容器管理0
@PropertySource(value = "classpath:/user.properties",
                encoding = "utf-8")
public class HelloController {

    //private String name="李四";
    //动态赋值
    /**
     * 表达式：springel 表达式 简称spel表达式
     *  语法： ${表达式内容}
     *  工作原理：
     *         容器：在内存中一个存储大量数据的集合
     *         1.当SpringBoot程序启动时，首先加载application.yml的配置文件
     *         2.当程序加载key-value结构时，将数据保存到Map集合中（容器内部）
     *         3.利用Spel表达式，通过key，获取value，之后为属性赋值
     * */
    @Value("${userinfo.name}")
    private String name;

    @Value("${user.info2}")
    private String name2;

    /*该注解与方法绑定*/
    @RequestMapping("/hello")
    public String show(){
        return "欢迎使用SpringBoot"+name+name2;
    }

    //SpringMVC如果返回对象,则页面显示JSON
    @RequestMapping("/getUser")
    public User get(){
        User user = new User();
        user.setId(101).setName("jack").setAge(25).setSex("嬲");
        return user;
    }
}
