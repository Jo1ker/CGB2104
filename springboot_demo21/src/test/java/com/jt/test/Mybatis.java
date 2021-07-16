package com.jt.test;

import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class Mybatis {
    @Autowired
    private UserMapper userMapper;
    //1.根据id主键 查询id=1的数据
    @Test
    public void testFind(){
        System.out.println(userMapper.getClass());
        List<User> userlist = userMapper.findAll();
        System.out.println(userlist);
    }

    @Test
    public void findById(){
        User userlist = userMapper.findById(11);
        System.out.println(userlist);
    }

    @Test
    public void testAdd(){
        User user = new User();
        user.setName("卡普空").setAge(50).setSex("男");
        userMapper.add(user);
    }

    @Test
    public void testUpdate(){
        User user = new User();
        user.setName("卡普空").setAge(999).setSex("女");
        userMapper.update(user);
    }

    @Test
    public void testDelete(){
        userMapper.delete(253);
    }
}
