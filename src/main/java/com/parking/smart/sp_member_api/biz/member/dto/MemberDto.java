package com.parking.smart.sp_member_api.biz.member.dto;

import com.parking.smart.sp_member_api.biz.member.entity.Role;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private String memberId;

    private String password;

    private String name;

    private String age;

    private Role role;

}
