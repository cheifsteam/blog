package com.qrx.blog.excpetion;

/**
 * @author 屈燃希
 * @version 1.0
 * @project
 */

import com.qrx.blog.util.AjaxResult;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 控制器异常处理类
 */

/**
 * Rest控制器异常处理类
 */
@RestControllerAdvice(annotations = RestController.class) // 全局异常处理
public class RestControllerExceptionHandler {
    /**
     * 处理BaseException类(及其子类)的异常
     */
    @ExceptionHandler({ MyException.class })
    public AjaxResult baseExceptionHandler(MyException e) {
        return AjaxResult.error(e.getCode(),e.getMsg());
    }
    /**
     * 处理Exception类的异常
     */
    /**
     * 处理DuplicateKeyException类(主键重复异常)的异常
     */
    @ExceptionHandler({ DuplicateKeyException.class })
    public AjaxResult DuplicateKeyExceptionHandler() {
        return AjaxResult.error("主键重复异常");
    }
//    @ExceptionHandler({ Exception.class })
//    public AjaxResult exceptionHandler() {
//        return AjaxResult.error();
//    }
}


