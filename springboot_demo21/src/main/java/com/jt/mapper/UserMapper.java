package com.jt.mapper;

import com.jt.pojo.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface UserMapper {
//    @Select("select * from demo_user")
    List<User> findAll();

    User findById(Integer i);

    void add(User user);

    void update(User user);

    void delete(Integer i);
}
