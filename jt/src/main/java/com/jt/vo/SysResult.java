package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysResult {

    private Integer status;//状态码信息
    private String msg;    //提示信息
    private Object data;   //封装业务数据

    public static SysResult fail(){
        return new SysResult(201, "服务器调用失败", null);
    }

    public static SysResult success(){
        return new SysResult(200, "业务执行成功", null);
    }

    public static SysResult success(Object data){
        return new SysResult(200, "业务执行成功", data);
    }

    public static SysResult success(String msg , Object data){
        return new SysResult(200, msg, data);
    }
}
