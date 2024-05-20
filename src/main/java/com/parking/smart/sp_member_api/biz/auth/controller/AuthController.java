package com.parking.smart.sp_member_api.biz.auth.controller;

import com.parking.smart.sp_member_api.biz.auth.dto.AuthRequest;
import com.parking.smart.sp_member_api.biz.common.model.CommonResponse;
import com.parking.smart.sp_member_api.biz.member.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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

    @PostMapping
    public CommonResponse<?> authenticate(@RequestBody @Valid AuthRequest authRequest) {
        var jwtToken = memberService.createTokenInfoFrom(authRequest);
        return new CommonResponse<>().success(jwtToken);
    }

    @PostMapping("/refresh-token")
    public CommonResponse<?> createRefreshToken(@RequestBody @NotNull String refreshToken) {
        var memberTokenInfo = memberService.createTokenInfoFrom(refreshToken);
        return new CommonResponse<>().success(memberTokenInfo);
    }

}
