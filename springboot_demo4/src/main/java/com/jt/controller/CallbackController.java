package com.jt.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class CallbackController {

    //测试回调地狱函数
    @GetMapping("/getA")
    public String getA(){
        return "A";
    }

    @GetMapping("/getB")
    public String getB(String str){
        return str + "B";
    }

    @GetMapping("/getC")
    public String getC(String str){
        return str + "C";
    }

}
