package com.parking.smart.sp_member_api.biz.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JoinDto {

    @NotBlank
    private String memberId;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String age;

}
