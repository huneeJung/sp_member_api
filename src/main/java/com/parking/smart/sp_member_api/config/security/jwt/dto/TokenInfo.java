package com.parking.smart.sp_member_api.config.security.jwt.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    private String token;
    @JsonIgnore
    private String refreshToken;
    @JsonIgnore
    private Date issueDate;
    @JsonIgnore
    private Date expireDate;

    public TokenInfo(String memberId, String memberNm) {
        this.memberId = memberId;
        this.memberNm = memberNm;
        this.issueDate = new Date();
    }

    public static TokenInfo from(Member member) {
        return TokenInfo.builder()
                .memberId(member.getMemberId())
                .memberNm(member.getName())
                .role(member.getRole().name())
                .issueDate(new Date())
                .build();
    }
}
