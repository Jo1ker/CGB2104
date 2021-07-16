package com.jt.service;

import com.jt.pojo.User;
import com.jt.vo.PageResult;

import java.util.List;

public interface UserService {

    List<User> getUser();

    String login(User user);

    PageResult getUserList(PageResult pageResult);

    void updateStatus(User user);

    void addUser(User user);

    void deleteUserById(Integer id);

    User getUserById(Integer id);

    void updateUser(User user);
}
