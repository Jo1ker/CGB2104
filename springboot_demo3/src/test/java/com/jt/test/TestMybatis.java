package com.jt.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.util.List;

@SpringBootTest
public class TestMybatis {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testFind(){
        System.out.println(userMapper.findAll());
    }

    //思想: 以对象的方式操作数据库 单表几乎不写Sql
    //简化CURD操作
    @Test
    public void testInsert(){
        User user = new User();
        user.setName("love").setAge(19).setSex("女");
        userMapper.insert(user);
    }

    //1.根据id主键 查询id=1的数据
    //SELECT id,name,age,sex FROM demo_user WHERE id=?
    @Test
    public void testSelect1(){
        User user = userMapper.selectById(1);
        System.out.println(user);
    }

    //2.查询name="小乔" sex="男"的用户
    //Sql: select * from demo_user where name="xx" and sex="xx"
    @Test
    public void testSelect2(){
        //创建条件构造器 封装where条件的
        User user = new User();
        user.setName("小乔").setSex("男");
        //实现时会动态的根据对象中不为null的属性，拼接where条件，
        //默认的关系链接符 and
        QueryWrapper queryWrapper = new QueryWrapper(user);
        List<User> userList= userMapper.selectList(queryWrapper);
        System.out.println(userList);
    }

    //3.查询name="小乔" sex="男"的用户
    //Sql:SELECT id,name,age,sex FROM demo_user WHERE name=? AND sex=?
    @Test
    public void testSelect3(){
        //创建条件构造器 封装where条件的
        //实现时会动态的根据对象中不为null的属性，拼接where条件，
        //默认的关系链接符 and
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name","小乔")
                    .eq("sex","男");
        List<User> userList= userMapper.selectList(queryWrapper);
        System.out.println(userList);
    }

    //4.查询 age>18 sex="女"的用户
    //Sql:SELECT * demo_user WHERE age > 18 and sex="女"
    //逻辑运算符 > gt, < lt, = eq,>= ge,<= le,!= ne
    @Test
    public void testSelect4(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("age",18)
                .eq("sex","女");
        List<User> userList= userMapper.selectList(queryWrapper);
        System.out.println(userList);
    }

    //5.like关键字
    //5.1 查询name包含“乔”  where name like '%乔%'
    //5.2 查询name以“乔”结尾的  where name like '%乔'
    @Test
    public void testSelect5(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //queryWrapper.like("name","乔");
        queryWrapper.likeLeft("name","乔");
        List<User> userList= userMapper.selectList(queryWrapper);
        System.out.println(userList);
    }

    //6.in关键字
    //需求：查询ID为1,3,5,6的数据 select * from demo_user where id in(1,3,5,6)
    //java基础：参数中使用...表示可变参数类型 多个参数,号分隔
    //          一般定义可变参数类型时一般位于方法的最后一位
    //          可变参数类型的实质 就是数组，写法不同
    @Test
    public void testSelect6(){
        //一般的数组采用包装类型，使用对象身上的方法 基本类型没有方法
        Integer[] ids = {1,3,5,6};
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //采用可变参数类型 实现查询
        //queryWrapper.in("id",1,3,5,6);
        queryWrapper.in("id",ids);
        List<User> userList= userMapper.selectList(queryWrapper);
        System.out.println(userList);
    }

    /* public void inxxx(String name,Integer age,Integer... id){

    }*/

    /**
     * 关键字：order by
     * 默认规则： 升序 asc 降序 desc
     * 需求：查询性别为男的用户并且按照年龄降序排列
     * Sql: select * from demo_user where sex="男" order by age desc
     * */
    @Test
    public void testSelect7(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sex","男")
                    .orderByDesc("age");
        List<User> userList= userMapper.selectList(queryWrapper);
        System.out.println(userList);
    }

    /**
     * 动态SQL：
     * 根据用户的条件，动态拼接where条件
     * 案例:根据sex，age查询数据
     * 1.
     * API说明:
     *      queryWrapper.gt(判断条件,字段名称,字段值)
     *      判断条件: true  则动态的拼接where条件
     *              false 不会拼接where条件
     *  判断语句:
     *     Boolean sexBoo = (sex !=null) && sex.length()>0;
     * */
    @Test
    public void testSelect8(){
        Integer age = null;
        String sex = null;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Boolean ageBoo = (age != null);
        Boolean sexBoo = StringUtils.hasLength(sex);
        queryWrapper.gt(ageBoo,"age",age)
                    .eq(sexBoo,"sex","女");
        List<User> userList= userMapper.selectList(queryWrapper);
        System.out.println(userList);
    }

    /**
     * 练习9:只获取主键ID的值
     * Sql:select id from demo_user
     * */
    @Test
    public void testSelect9(){
        List list = userMapper.selectObjs(null);
        System.out.println(list);
    }

    /**
     * 练习10:删除name="xxx"的数据
     * */
    @Test
    public void testDelete(){
        //删除ID为100的数据
        userMapper.deleteById(100);
        //userMapper.deleteBatchIds(null);
        //删除操作的条件构造器
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name","xxx");
        userMapper.delete(queryWrapper);
    }

    /**
     * 练习11:数据修改
     * 案例1：要求修改id=233 name改为="晚上吃什么"
     * API说明：userMapper.updateById(对象信息)
     * */
    @Test
    public void testUpdate1(){
        //修改除ID之外的所有不为null的数据，id当做where一位条件
        User user = new User();
        user.setName("晚上吃什么").setId(251);
        userMapper.updateById(user);
    }

    /**
     * 练习12:数据修改
     * 案例1：要求修改id=mp name改为="宵夜吃什么" age=20 sex="女"
     * API说明：
     *              userMapper.update(对象,修改条件构造器)
     *               对象: 修改后的数据使用对象封装
     *               修改条件构造器: 负责修改的where条件
     * */
    @Test
    public void testUpdate2(){
       User user = new User();
       user.setName("宵夜吃什么").setAge(20).setSex("女");
       UpdateWrapper updateWrapper = new UpdateWrapper();
       updateWrapper.eq("name","MP");
       userMapper.update(user,updateWrapper);
    }

}
