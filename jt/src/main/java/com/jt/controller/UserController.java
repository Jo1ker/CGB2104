package com.jt.controller;

import com.jt.pojo.User;
import com.jt.service.UserService;
import com.jt.vo.PageResult;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController    //返回值都是JSON串
@CrossOrigin       //解决跨域问题 url/ajaxurl
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * URL地址：/user/login
     * 参数：对象的JSON串
     * 返回值结果：SysResult对象
     * 业务需求：如果用户登录成功，返回密钥token
     *         如果用户没有登录成功，返回null
     * */
    @PostMapping("/login")
    public SysResult login(@RequestBody User user){
        String token = userService.login(user);
        if (StringUtils.hasLength(token)){
            return SysResult.success(token);
        }
        //如果数据有值则返回成功
        return SysResult.fail();
        //return new SysResult(200, "业务执行成功", user);
    }

    /**
     * 需求：利用分页展现user列表数据局、
     * URL: /user/list get
     * 参数： url?query=查询关键字&pageNum=1&pageSize=10
     * 返回值：SysResult对象（pageResult对象）
     * */
    @GetMapping("/list")
    public SysResult getUserList(PageResult pageResult){
        pageResult = userService.getUserList(pageResult);
        return SysResult.success(pageResult);
    }

    /**
     * 需求: 根据ID修改用户的状态信息
     * URL: /user/status/{id}/{status}
     * 参数: 2个参数  用户对象接收
     * 返回值: SysResult对象
     */
    @PutMapping("/status/{id}/{status}")
    public SysResult updateStatus(User user){
        userService.updateStatus(user);
        return SysResult.success();
    }

    /**
     * 需求：实现用户新增 设定状态true，创建时间/修改时间
     * URL: /user/addUser
     * 参数：form表单对象 利用User对象接收
     * 返回值：SysResult对象
     * */
    @PostMapping("/addUser")
    public SysResult addUser(@RequestBody User user){
        userService.addUser(user);
        return SysResult.success();
    }

    @DeleteMapping("/{id}")
    public SysResult deleteUserById(@PathVariable Integer id){
        userService.deleteUserById(id);
        return SysResult.success();
    }

    @GetMapping("/{id}")
    public SysResult getUserById(@PathVariable Integer id){
        User user = userService.getUserById(id);
        return SysResult.success(user);
    }

    @PutMapping("/updateUser")
    public SysResult updateUser(@RequestBody User user){
        userService.updateUser(user);
        return SysResult.success();
    }


}
