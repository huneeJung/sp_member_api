package com.parking.smart.sp_member_api.biz.common.exception;

public class AlertException extends RuntimeException {

    private final String message;

    public AlertException(String message) {
        super(message);
        this.message = message;
    }
}
