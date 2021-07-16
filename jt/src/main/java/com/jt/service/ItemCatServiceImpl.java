package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemCatServiceImpl implements ItemCatService{
    @Autowired
    private ItemCatMapper itemCatMapper;

    /**
     * 1.准备Map集合,实现数据封装
     * Map<key,value> = Map<parentId,List<ItemCat>>
     * 2.封装业务说明
     *      map中key~~parentId
     *         不存在:可以存储该key
     *               同时封装一个List集合,将自己作为第一个元素封装到其中.
     *         存在: 根据key获取所有子级集合,将自己追加进去 形成第二个元素.
     */

    public Map<Integer,List<ItemCat>> itemCatMap(){
        //1.定义Map集合
        Map<Integer,List<ItemCat>> map = new HashMap<>();
        //2.查询所有的数据库信息 1-2-3
        List<ItemCat> list = itemCatMapper.selectList(null);
        for(ItemCat itemCat :list){
            int parentId = itemCat.getParentId();//获取父级ID
            if(map.containsKey(parentId)){ //判断集合中是否已经有parentId
                //有key  获取list集合 将自己追加到集合中
                List<ItemCat> exeList = map.get(parentId);//引用对象
                exeList.add(itemCat);
            }else{
                //没有key,将自己封装为第一个list元素
                List<ItemCat> firstList = new ArrayList<>();
                firstList.add(itemCat);
                map.put(parentId,firstList);
            }
        }
        return map;
    }

    @Override
    public List<ItemCat> findItemCatList(Integer level) {
        long startTime = System.currentTimeMillis();
        Map<Integer,List<ItemCat>> map = itemCatMap();
        //1.如果level=1 说明获取一级商品分类信息 parent_id=0
        if(level == 1){
            return map.get(0);
        }

        if(level == 2){ //获取一级和二级菜单信息
            return getTwoList(map);
        }

        //3.获取三级菜单信息
        //3.1获取二级商品分类信息  BUG:有的数据可能没有子级 如何处理
        List<ItemCat> oneList = getTwoList(map);
        for (ItemCat oneItemCat : oneList){
            //从一级集合中,获取二级菜单列表
            List<ItemCat> twoList = oneItemCat.getChildren();
            //bug解决: 如果该元素没有2级列表,则跳过本次循环,执行下一次操作
            if(twoList == null || twoList.size()==0){
                continue;
            }
            for(ItemCat twoItemCat : twoList){
                //查询三级商品分类  条件:parentId=2级ID
                List<ItemCat> threeList = map.get(twoItemCat.getId());
                twoItemCat.setChildren(threeList);
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时:"+(endTime - startTime)+"毫秒");
        return oneList;
    }


    public List<ItemCat> getTwoList(Map<Integer,List<ItemCat>> map){
        List<ItemCat> oneList = map.get(0);
        for (ItemCat oneItemCat : oneList){//查询二级 parentId=1级Id
            List<ItemCat> twoList = map.get(oneItemCat.getId());
            oneItemCat.setChildren(twoList);
        }
        //二级嵌套在一级集合中,所有永远返回的都是1级.
        return oneList;
    }


    /**
     * 3层商品分类嵌套  1一级分类(children(2级商品分类))
     *                2一级分类(children(3级商品分类))
     * 一级查询条件   parent_id=0
     * 二级查询条件   parent_id=一级的ID
     * 三级查询条件   parent_id=二级的ID

     */
   /* @Override
    public List<ItemCat> findItemCatList(Integer level) {
        long startTime = System.currentTimeMillis();
        //1.查询一级商品分类信息
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id",0 );
        List<ItemCat> oneList = itemCatMapper.selectList(queryWrapper);
        //2.查询二级商品分类信息
        for (ItemCat oneItemCat : oneList){
            queryWrapper.clear();
            queryWrapper.eq("parent_id",oneItemCat.getId());
            List<ItemCat> twoList = itemCatMapper.selectList(queryWrapper);
            //3.查询三级商品分类信息
            for (ItemCat twoItemCat : twoList){
                queryWrapper.clear();
                queryWrapper.eq("parent_id",twoItemCat.getId());
                List<ItemCat> threeList = itemCatMapper.selectList(queryWrapper);
                twoItemCat.setChildren(threeList);
            }
            oneItemCat.setChildren(twoList);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("业务的执行时间为:"+(endTime-startTime)+"毫秒");
        return oneList;
    }*/

    @Override
    @Transactional
    public void updateStatus(ItemCat itemCat) {
        itemCatMapper.updateById(itemCat);
    }

    @Override
    @Transactional
    public void saveItemCat(ItemCat itemCat) {
        itemCatMapper.insert(itemCat);
    }

    @Override
    @Transactional
    public void updateItemCat(ItemCat itemCat) {
        itemCatMapper.updateById(itemCat);
    }

    @Override
    @Transactional
    public void deleteItemCat(ItemCat itemCat) {
        //1.判断是否为三级菜单
        if (itemCat.getLevel() == 3){
            //如果是三级则直接删除
            itemCatMapper.deleteById(itemCat.getId());
            return;//程序终止
        }

        //2.检查菜单是否为二级
        if(itemCat.getLevel() == 2){
            //应该先删除三级 根据parent_id= 2级ID即可
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("parent_id", itemCat.getId());
            itemCatMapper.delete(queryWrapper);
            //再删除2级菜单
            itemCatMapper.deleteById(itemCat.getId());
            return;
        }

        //删除1级商品分类信息 先删除3级，再删除2级
        //1.首先获取商品分类2级信息 parent_id= 1级ID
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id", itemCat.getId());
        List<ItemCat> twoList = itemCatMapper.selectList(queryWrapper);
        for (ItemCat twoItemCat:twoList){
            queryWrapper.clear();
            queryWrapper.eq("parent_id", twoItemCat.getId());
            itemCatMapper.delete(queryWrapper);
            itemCatMapper.deleteById(twoItemCat.getId());
        }
        itemCatMapper.deleteById(itemCat.getId());
    }

}
