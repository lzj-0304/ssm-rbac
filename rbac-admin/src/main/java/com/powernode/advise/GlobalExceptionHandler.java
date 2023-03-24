package com.powernode.advise;

import com.powernode.common.Result;
import com.powernode.enums.ResultEnums;
import com.powernode.enums.StateEnums;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Mr.T
 * @Date: 2023/3/16
 */

@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 全局异常处理方法
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e){
        return new Result(ResultEnums.ERROR,e.getMessage());
    }


}
