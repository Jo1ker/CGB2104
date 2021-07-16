package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.selectList(null);
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> getUserByNS(User user) {
        return userMapper.selectList(new QueryWrapper<>(user));
    }

    @Override
    public List<User> getUserByName(String name) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("name",name);
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public void deleteByName(User user) {
        userMapper.delete(new QueryWrapper<>(user));
    }

    @Override
    public void deleteUser(User user) {
        userMapper.delete(new QueryWrapper<>(user));
    }

    @Override
    public void deleteUserById(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    public void saveUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateById(user);
    }
}
