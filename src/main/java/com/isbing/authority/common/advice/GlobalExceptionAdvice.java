package com.isbing.authority.common.advice;

import com.isbing.authority.common.entity.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 * Created by songbing
 * Created time 2019/3/16 下午7:14
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {


    /**
     * 不是业务异常的 其余异常的捕捉
     * @param req
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public CommonResponse handlerException(HttpServletRequest req, Exception ex) {
        return CommonResponse.builder()
                .code(500)
                .message(ex.getMessage())
                .data(null)
                .build();
    }
}
