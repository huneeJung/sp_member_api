package com.parking.smart.sp_member_api.biz.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.parking.smart.sp_member_api.biz.common.constant.Constant.HttpConstant.SUCCESS_CODE;
import static com.parking.smart.sp_member_api.biz.common.constant.Constant.HttpConstant.SUCCESS_MESSAGE;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {

    private Boolean success;
    private Integer code;
    private String message;
    private T result;

    public CommonResponse success(T result) {
        return CommonResponse.builder()
                .success(true)
                .code(SUCCESS_CODE)
                .message(SUCCESS_MESSAGE)
                .result(result)
                .build();
    }

}
