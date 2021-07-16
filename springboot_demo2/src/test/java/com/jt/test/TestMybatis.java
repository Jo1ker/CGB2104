package com.jt.test;

import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestMybatis {

    @Autowired
    private UserMapper userMapper;

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
        user.setName("evol").setAge(1000).setSex("男");
        userMapper.insert(user);
        System.out.println("新增成功");

    }

    @Test
    public void testDelete(){
        userMapper.delete();
    }

    @Test
    public void testUpdate(){
        User user = new User();
        user.setName("evol").setAge(20).setSex("女");
        userMapper.updateByName(user);
    }

}
