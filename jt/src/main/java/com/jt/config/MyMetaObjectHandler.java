package com.jt.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component //将对象交给Spring容器管理 不属于C/S/M
public class MyMetaObjectHandler implements MetaObjectHandler {
    //入库操作时调用 created/updated
    //setFieldValByName(arg1,arg2 ,arg3 )
    //arg1:自动填充的字段名称 arg2:自动填充的值 arg3:metaObject 固定写法
    @Override
    public void insertFill(MetaObject metaObject) {
        //设定时间变量
        Date date = new Date();
        this.setFieldValByName("created", date, metaObject);
        this.setFieldValByName("updated", date, metaObject);
    }
    //更新操作时调用 updated
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updated", new Date(), metaObject);
    }
}
