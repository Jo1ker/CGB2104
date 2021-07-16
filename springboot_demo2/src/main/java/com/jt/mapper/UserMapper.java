package com.jt.mapper;

import com.jt.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
//@Mapper//spring为该接口创建一个代理对象
public interface UserMapper {
    List<User> findAll();

    @Select("select id,name,age,sex from demo_user where id=#{id}")
    User findById(int id);

    //#{name} 从对象中获取指定的属性的值  #有预编译的效果 防止Sql注入攻击
    @Insert("insert into demo_user(id,name,age,sex) values (null,#{name},#{age},#{sex})")
    void insert(User user);

    void delete();

    @Update("update demo_user set age = #{age},sex=#{sex} where name= #{name}")
    void updateByName(User user);

}
