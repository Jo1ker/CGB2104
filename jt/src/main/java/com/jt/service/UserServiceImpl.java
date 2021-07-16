package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public List<User> getUser() {
        return userMapper.selectList(null);
    }

    /**
     * 说明: 根据用户名和密码判断数据是否有效
     * 步骤:
     *      1.将明文进行加密处理. md5加密/md5Hash/sha1
     *      2.根据加密后的数据查询数据库
     *      3.判断结果是否有效
     *          有值: 返回特定字符串token=123
     *          null: u/p 不正确  返回null
     *  关于环境问题说明:
     *    A计算机    md5(abc) = "123" 正确的  99%
     *    个别计算机  md5(abc) = "567" 偶然   1%
     *    修改数据库中的密码!!!
     */
    @Override
    @Transactional
    public String login(User user) {
        //1.获取原始的密码
        String password = user.getPassword();
        //2.将密码进行加密处理
        String md5Str = DigestUtils.md5DigestAsHex(password.getBytes());
        //3.将密文传递给对象
        user.setPassword(md5Str);
//        QueryWrapper<User> queryWrapper = new QueryWrapper();
//        queryWrapper.eq("username",user.getUsername())
//                .eq("password", user.getPassword());
        //User userDB = userMapper.selectOne(new QueryWrapper<>(user));
        QueryWrapper<User> queryWrapper = new QueryWrapper(user);
        //4.根据条件查询数据库
        User userDB = userMapper.selectOne(queryWrapper);
        //5.定义token数据 限定条件 token不能重复
        // UUID: 随机字符串 3.4*10^38 种可能性  hash(时间戳+随机数)
        String uuid = UUID.randomUUID().toString()
                .replace("-", "");
        return userDB == null ? null : uuid;
    }



    /**
     * Mysql的分页语法: 1.0版本
     *  select * from user limit 起始位置0,查询的条数
     *  Sql: 1页2条   n页数/m条数
     *      SELECT * FROM  USER LIMIT 0,2   第一页
     *      SELECT * FROM  USER LIMIT 2,2   第二页
     *      SELECT * FROM  USER LIMIT 4,2   第三页
     *      SELECT * FROM  USER LIMIT 6,2   第四页
     *      SELECT * FROM  USER LIMIT (n-1)m,m   第N页
     * @param pageResult
     * @return
     */
    
//    @Override
//    public PageResult getUserList(PageResult pageResult) {
//        //1.获取总记录数
//        long total = userMapper.selectCount(null);
//        //2.获取分页后的结果
//        int pageNum = pageResult.getPageNum();              //页数
//        int pageSize = pageResult.getPageSize();            //行数
//        int startIndex = (pageNum - 1) * pageSize;          //起始位置
//        List<User> rows = userMapper.getUserList(startIndex,pageSize);
//        //List<User> rows = userMapper.getUserList2(pageResult); //利用对象查询
//        //将数据进行封装
//        pageResult.setTotal(total).setRows(rows);           //+2个参数
//        return pageResult;                                  //最终5个参数返回
//    }


    /**
     * 利用MP的方式实现分页查询
     * API说明: selectPage(arg1,arg2)
     * arg1:   MP中的分页对象 固定的
     * arg2:   MP分页中的条件构造器
     * @param pageResult
     * @return
     * 动态Sql: select * from user where username like "admin"
     * 条件: 如果用户传递query则拼接where条件 否则不拼接where条件
     */
    @Override
    @Transactional
    public PageResult getUserList(PageResult pageResult) {
        //1.定义MP的分页对象
        IPage iPage = new Page(pageResult.getPageNum(),pageResult.getPageSize());
        //2.构建查询条件构造器
        QueryWrapper queryWrapper = new QueryWrapper();
        //判断用户数据是否有效 有效 true 无效 false
        boolean flag = StringUtils.hasLength(pageResult.getQuery());
        queryWrapper.like(flag, "username", pageResult.getQuery());
        //经过MP分页查询将所有的分页(total/结果/页面/条数/xxx)数据封装到ipage对象
        iPage = userMapper.selectPage(iPage,queryWrapper);
        //从分页对象中获取总记录数
        long total = iPage.getTotal();
        //从分页对象中获取分页后的结果
        List<User> rows = iPage.getRecords();
        return pageResult.setTotal(total).setRows(rows);
    }

    @Override
    @Transactional
    public void updateStatus(User user) {//id/status
        //根据对象中不为null的属性充当set条件, id当作唯一where条件
        userMapper.updateById(user);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        String password = user.getPassword();
        String md5Str = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setStatus(true).setPassword(md5Str);
        userMapper.insert(user);
    }

    @Override
    //@Transactional(rollbackFor = Exception.class)
    @Transactional
    public void deleteUserById(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    @Transactional
    public User getUserById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userMapper.updateById(user);
    }

}
