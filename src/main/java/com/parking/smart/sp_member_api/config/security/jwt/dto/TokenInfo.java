package com.parking.smart.sp_member_api.config.security.jwt.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.parking.smart.sp_member_api.biz.member.dto.MemberDto;
import com.parking.smart.sp_member_api.biz.member.entity.Member;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenInfo {

    private String memberId;
    private String memberNm;
    private String role;
    @JsonIgnore
    private Date issueDate;

    @JsonIgnore
    private String token;
    @JsonIgnore
    private String refreshToken;

    public static TokenInfo from(MemberDto member) {
        return TokenInfo.builder()
                .memberId(member.getMemberId())
                .memberNm(member.getName())
                .role(member.getRole().name())
                .issueDate(new Date())
                .build();
    }
}
