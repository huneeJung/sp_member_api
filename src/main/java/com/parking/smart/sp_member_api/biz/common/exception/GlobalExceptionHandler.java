package com.parking.smart.sp_member_api.biz.common.exception;

import com.parking.smart.sp_member_api.biz.common.model.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.parking.smart.sp_member_api.biz.common.constant.Constant.HttpConstant.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public CommonResponse<?> handleException(RuntimeException e) {
        log.error("", e);
        if (e instanceof AlertException) {
            return CommonResponse.builder()
                    .success(false)
                    .code(CLIENT_FAIL_CODE)
                    .message(FAIL_MESSAGE)
                    .result(e.getMessage())
                    .build();
        } else {
            return CommonResponse.builder()
                    .success(false)
                    .code(SERVER_FAIL_CODE)
                    .message(FAIL_MESSAGE)
                    .result("고객센터에 문의해주세요.")
                    .build();
        }
    }

}
