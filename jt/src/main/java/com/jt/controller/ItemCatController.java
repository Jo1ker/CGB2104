package com.jt.controller;

import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/itemCat")
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    /**
     *  URL: /itemCat/findItemCatList/{level}
     *  参数: level 1/2/3
     *  返回值: SysResult对象(List<ItemCat>)
     */
    @GetMapping("/findItemCatList/{level}")
    public SysResult findItemCatList(@PathVariable Integer level){
        List<ItemCat> itemCatList = itemCatService.findItemCatList(level);
        return SysResult.success(itemCatList);
    }

    /**
     *  url地址:/itemCat/status/{id}/{status}
     *  参数: 利用ItemCat对象接收
     *  返回值: SysResult对象
     */
    @PutMapping("/status/{id}/{status}")
    public SysResult updateStatus( ItemCat itemCat){
        itemCatService.updateStatus(itemCat);
        return SysResult.success();
    }

    /**
     * URL:  /itemCat/saveItemCat
     * 参数:  form表单提交 json
     * 返回值: SysResult对象
     */
    @PostMapping("/saveItemCat")
    public SysResult saveItemCat(@RequestBody ItemCat itemCat){
        itemCat.setStatus(true);
        itemCatService.saveItemCat(itemCat);
        return SysResult.success();
    }


    @PutMapping("/updateItemCat")
    public SysResult updateItemCat(@RequestBody ItemCat itemCat){
        itemCatService.updateItemCat(itemCat);
        return SysResult.success();
    }

    /**
     * 实现商品分类删除操作
     * URL: /itemCat/deleteItemCat?id=xxx&level=xxx
     * 返回值: SysResult对象
     */
    @DeleteMapping("/deleteItemCat")
    public SysResult deleteItemCat(ItemCat itemCat){
        itemCatService.deleteItemCat(itemCat);
        return SysResult.success();
    }
}
