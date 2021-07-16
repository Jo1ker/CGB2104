package com.jt.controller;

import com.jt.pojo.User;
import com.jt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/findAll")
    public List<User> findAll(){
        return userService.findAll();
    }

    /**
     * 业务：根据id查询数据库
     * 请求类型：get类型
     * 请求路径：http://localhost:8090/getUserById?id=1
     * 请求参数：id=1
     * 返回值结果：User对象
     * MVC:接收参数的名称，必须与传递参数的名称一致
     * */
    @GetMapping("/getUserById")
    public User getUserById(Integer id){
        return userService.getUserById(id);
    }

    /**
     * 业务: 根据name/sex查询数据
     * 请求类型: get请求
     * 请求路径: http://localhost:8090/getUserByNS
     * 请求参数: user对象方式
     * 返回值结果: List<User>
     * 数据?name=xx&sex=xx
     */
    @GetMapping("/getUserByNS")
    public List<User> getUserByNS(User user){
        return userService.getUserByNS(user);
    }


    /**
     * 业务: 模糊查询name="%xxx%"数据
     * 请求类型: get请求
     * 请求路径: http://localhost:8090/user/君
     * 请求参数: name属性
     * 返回值结果: List<User>
     * RestFul参数接收:
     *      1.多个参数使用/分割
     *      2.参数使用{}形式包裹
     *      3.接参使用特定的注解 @PathVariable
     */
    @GetMapping("/user/{name}")
    public List<User> getUserByName(@PathVariable String name){
        return userService.getUserByName(name);
    }

    //简化:restFul风格2 查询name="精" sex="女"
    //如果参数有多个,则可以使用对象封装. 要求参数名称与属性一致.
    @GetMapping("/user/{name}/{sex}")
    public List<User> getUserByName(User user){
        return null;
    }

    /**
     * 实现数据删除
     * URL: http://localhost:8090/deleteByName
     * 参数: 对象封装
     * 返回值: 删除数据成功
     */
    @DeleteMapping("/deleteByName")
    public String deleteByName(User user){
        userService.deleteByName(user);
        return "删除成功";
    }


    /**
     * URL: http://localhost:8090/user/宵夜吃什么/女
     * 参数: 对象封装
     * 返回值: "删除成功!!"
     */
    @DeleteMapping("/user/{name}/{sex}")
    public String deleteUser(User user){
        userService.deleteUser(user);
        return "删除成功";
    }

    /**
     * 根据ID删除数据
     * URL:http://localhost:8090/user/100
     * 参数:id
     * 返回值: String类型
     */
    @DeleteMapping("/user/{id}")
    public String deleteUserById(@PathVariable Integer id){
        userService.deleteUserById(id);
        return "删除id"+id+"成功";
    }

    /**
     * 需求：完成用户的入库操作
     * URL：http://localhost:8090/saveUser
     * 参数：对象的JSON串 {key:value}
     * 类型：post
     * 返回值：String
     * 难点：如何将JSON串转化为User对象
     * SpringMVC:针对于JSON与对象转化 开发2个注解
     *           1.@ResponseBody 将对象转化为JSON串
     *           2.@RequestBody 将JSON串转化为User对象
     * */
    @PostMapping("/saveUser")
    public String saveUser(@RequestBody User user){
        userService.saveUser(user);
        return "新增数据成功";
    }

    //用户新增restFul风格
    @PostMapping("/user/{name}/{age}/{sex}")
    public String saveUser2(User user){
        userService.saveUser(user);
        return "新增用户成功";
    }

    /**
     * 需求：完成用户的入库操作
     * URL：http://localhost:8090/saveUser
     * 参数：对象的JSON串 {key:value}
     * 类型：post
     * 返回值：String
     * 难点：如何将JSON串转化为User对象
     * SpringMVC:针对于JSON与对象转化 开发2个注解
     *           1.@ResponseBody 将对象转化为JSON串
     *           2.@RequestBody 将JSON串转化为User对象
     * */
    @PutMapping("/updateUser")
    public String updateUser(@RequestBody User user){
        userService.updateUser(user);
        return "修改数据成功";
    }

}
