package com.jt.controller;

import com.jt.pojo.Rights;
import com.jt.service.RightsService;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/rights")
public class RightsController {
    @Autowired
    private RightsService rightsService;

    /**
     * 需求：查询权限列表信息 只查询2级权限列表信息
     * URL： /rights/getRightsList
     * 参数：无
     * 返回值：SysResult对象（List集合）
     * */
    @GetMapping("/getRightsList")
    public SysResult getRightsList(){
        List<Rights> rightsList = rightsService.getRightsList();
        return SysResult.success(rightsList);
    }
}
