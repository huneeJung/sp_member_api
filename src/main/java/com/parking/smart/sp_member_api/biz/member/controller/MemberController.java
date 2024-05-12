package com.parking.smart.sp_member_api.biz.member.controller;

import com.parking.smart.sp_member_api.biz.common.model.CommonResponse;
import com.parking.smart.sp_member_api.biz.member.dto.JoinDto;
import com.parking.smart.sp_member_api.biz.member.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public CommonResponse<?> joinMember(@RequestBody @Valid JoinDto joinDto) {
        memberService.joinMember(joinDto);
        return new CommonResponse<>().success("회원가입 완료");
    }

    @PostMapping("/check")
    public CommonResponse<?> joinMember(@RequestBody @NotEmpty String memberId) {
        memberService.checkDuplicateId(memberId);
        return new CommonResponse<>().success("사용가능한 계정입니다");
    }

}
