package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.ItemVO;
import com.jt.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemDescMapper itemDescMapper;

    @Override
    public PageResult getItemList(PageResult pageResult) {
        IPage iPage = new Page(pageResult.getPageNum(),pageResult.getPageSize());
        QueryWrapper queryWrapper = new QueryWrapper();
        boolean flag = StringUtils.hasLength(pageResult.getQuery());
        queryWrapper.like(flag,"title",pageResult.getQuery());
        //执行分页查询，返回值依然是分页对象信息
        iPage = itemMapper.selectPage(iPage, queryWrapper);
        //
        long total = iPage.getTotal();
        List<Item> rows = iPage.getRecords();
        pageResult.setTotal(total).setRows(rows);
        return pageResult;
    }


    @Override
    @Transactional
    public void updateItem(Item item) {
        itemMapper.updateById(item);
    }

    @Override
    @Transactional
    public void updateItemStatus(Item item) {
        itemMapper.updateById(item);
    }

    @Override
    @Transactional
    public void deleteItemById(Integer id) {
        itemMapper.deleteById(id);
        itemDescMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void saveItem(ItemVO itemVO) {
        Item item = itemVO.getItem();
        item.setStatus(true);    //默认启用状态
        itemMapper.insert(item); //MP自动的实现了主键自动回显
        ItemDesc itemDesc = itemVO.getItemDesc();
        itemDesc.setId(item.getId());
        itemDescMapper.insert(itemDesc);
    }
}
