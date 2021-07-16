package com.jt.exception;


import com.jt.vo.SysResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//全局异常处理，内部依然采用环绕通知的方式
//异常处理之后返回JSON串
//该全局异常处理机制，捕获Controller层的异常(其他层向上抛出异常)
@RestControllerAdvice
public class MyException {
    /**
     * 业务：如果后端报错，应该及时提示前端用户，返回统一的对象
     *      SysResult对象 status=201/msg="xxx失败"
     * 注解说明：
     *      @ExceptionHandler(RuntimeException.class)
     *      当遇到某种异常时，全局异常处理机制有效
     * */
    @ExceptionHandler(RuntimeException.class)
    public Object exception(Exception e){
        //1.应该打印异常信息
        e.printStackTrace();
        //2.返回特定的响应数据
        return SysResult.fail();
    }
}
