package com.parking.smart.sp_member_api.config.security.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {

    private String memberId;
    private String name;
    private String bearerToken;
    private String refreshToken;
}
