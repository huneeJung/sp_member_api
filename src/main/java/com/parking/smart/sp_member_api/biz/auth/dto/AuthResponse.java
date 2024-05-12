package com.parking.smart.sp_member_api.biz.auth.dto;

import com.parking.smart.sp_member_api.config.security.jwt.dto.JwtToken;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String memberId;
    private String role;
    private JwtToken jwtToken;

}