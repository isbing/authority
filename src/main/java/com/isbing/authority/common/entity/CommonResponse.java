package com.isbing.authority.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一返回结果bean
 * Created by songbing
 * Created time 2019/3/16 下午6:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonResponse implements Serializable {
    private Integer code;
    private String message;
    private Object data;

    public CommonResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
