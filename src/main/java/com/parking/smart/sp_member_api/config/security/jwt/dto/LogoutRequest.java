package com.parking.smart.sp_member_api.config.security.jwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutRequest {

    private String memberId;
    private String token;
}
