package com.jt.controller;

import com.jt.pojo.Item;
import com.jt.service.ItemService;
import com.jt.vo.ItemVO;
import com.jt.vo.PageResult;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/getItemList")
    public SysResult getItemList(PageResult pageResult){
        pageResult = itemService.getItemList(pageResult);
        return SysResult.success(pageResult);
    }

    @PutMapping("/updateItemStatus")
    public SysResult updateItemStatus(@RequestBody Item item){
        itemService.updateItemStatus(item);
        return SysResult.success();
    }

    @PutMapping("/updateItem")
    public SysResult updateItem(@RequestBody Item item){
        itemService.updateItem(item);
        return SysResult.success();
    }

    @DeleteMapping("/deleteItemById")
    public SysResult deleteItemById(Integer id){
        itemService.deleteItemById(id);
        return SysResult.success();
    }

    /**
     * URL地址： http://localhost:8091/item/saveItem
     * 请求参数： form表单的提交对象~~~JSON串 ItemVO对象接收
     * 返回值： SysResult对象
     * */
    @PostMapping("/saveItem")
    public SysResult saveItem(@RequestBody ItemVO itemVO){
        itemService.saveItem(itemVO);
        return SysResult.success();
    }
}
