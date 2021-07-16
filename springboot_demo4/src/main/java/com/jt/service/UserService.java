package com.jt.service;

import com.jt.pojo.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User getUserById(Integer id);

    List<User> getUserByNS(User user);

    List<User> getUserByName(String name);

    void deleteByName(User user);

    void deleteUser(User user);

    void deleteUserById(Integer id);

    void saveUser(User user);

    void updateUser(User user);
}
