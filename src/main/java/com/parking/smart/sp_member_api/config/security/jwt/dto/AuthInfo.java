package com.parking.smart.sp_member_api.config.security.jwt.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuthInfo {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private boolean valid;
    private TokenInfo tokenInfo;
}
