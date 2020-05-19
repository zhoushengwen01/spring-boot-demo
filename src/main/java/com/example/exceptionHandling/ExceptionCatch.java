package com.example.exceptionHandling;


import com.example.response.CommonCode;
import com.example.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常捕获类
 **/
@ControllerAdvice//控制器增强
@Slf4j
public class ExceptionCatch {

    //捕获 CustomException异常,及项目中可预知异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException e) {
        log.error("catch exception : {}\r\nexception: ", e.getMessage(), e);
        return new ResponseResult(e.getResultCode());
    }


    //捕获Exception不可预知异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception) {
        exception.printStackTrace();
        log.error("catch exception:{}", exception.getMessage());
        return new ResponseResult(CommonCode.NOT_FORESEE_EXCEPTION);
    }
}
