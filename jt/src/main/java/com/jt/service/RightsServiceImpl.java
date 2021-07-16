package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.RightsMapper;
import com.jt.pojo.Rights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RightsServiceImpl implements RightsService{
    @Autowired
    private RightsMapper rightsMapper;

    /**
     * 1.查询所有的1级信息 parent_id=0
     * 2.查询1级下边的
     * */
    @Override
    public List<Rights> getRightsList() {
        //1.查询一级权限信息
        QueryWrapper<Rights> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", 0);
        List<Rights> oneList = rightsMapper.selectList(queryWrapper);
        //2.查询1级下边的二级List集合
        for (Rights rights : oneList){
            queryWrapper.clear();//将where条件清空重新添加
            queryWrapper.eq("parent_id", rights.getId());
            List<Rights> children = rightsMapper.selectList(queryWrapper);
            rights.setChildren(children);
        }
        return oneList;
    }
}
