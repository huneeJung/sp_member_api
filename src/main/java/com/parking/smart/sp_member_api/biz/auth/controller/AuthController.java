package com.parking.smart.sp_member_api.biz.auth.controller;

import com.parking.smart.sp_member_api.biz.auth.dto.AuthRequest;
import com.parking.smart.sp_member_api.biz.auth.dto.AuthResponse;
import com.parking.smart.sp_member_api.biz.common.model.CommonResponse;
import com.parking.smart.sp_member_api.biz.member.service.MemberService;
import com.parking.smart.sp_member_api.config.security.jwt.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/member/auth")
public class AuthController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public CommonResponse<?> authenticate(@RequestBody @Valid AuthRequest authRequest) {
        var memberTokenInfo = memberService.findByMemberId(authRequest);
        var jwtDto = jwtTokenProvider.issue(memberTokenInfo);
        return new CommonResponse<>().success(
                AuthResponse.builder()
                        .memberId(memberTokenInfo.getMemberId())
                        .role(memberTokenInfo.getRole())
                        .jwtToken(jwtDto)
                        .build()
        );
    }

}
