package com.parking.smart.sp_member_api.biz.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    @NotBlank
    private String memberId;
    @NotBlank
    private String password;

}