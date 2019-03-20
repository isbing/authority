package com.isbing.authority.common.advice;

import com.isbing.authority.common.entity.CommonResponse;
import com.isbing.authority.common.entity.NoWapperResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 对返回结果的统一封装
 * Created by songbing
 * Created time 2019/3/16 下午6:47
 */
@ControllerAdvice
public class CommonResponseDataAdvice implements ResponseBodyAdvice{

    /**
     * 当有 NoWapperResponse注解时、不进行结果的封装
     * @param methodParameter
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        if (methodParameter.getDeclaringClass().isAnnotationPresent(
                NoWapperResponse.class
        )) {
            return false;
        }

        if (methodParameter.getMethod().isAnnotationPresent(
                NoWapperResponse.class
        )) {
            return false;
        }
        return true;
    }

    /**
     * 返回正常结果的封装
     * @param o
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        CommonResponse response = new CommonResponse(200, "");
        if (null == o) {
            return response;
        } else if (o instanceof CommonResponse) {
            response = (CommonResponse) o;
        } else {
            response.setData(o);
        }
        return response;
    }
}
