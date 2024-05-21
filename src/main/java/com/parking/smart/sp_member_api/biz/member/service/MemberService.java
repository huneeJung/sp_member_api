package com.parking.smart.sp_member_api.biz.member.service;

import com.parking.smart.sp_member_api.biz.common.exception.AlertException;
import com.parking.smart.sp_member_api.biz.common.mapper.MemberMapper;
import com.parking.smart.sp_member_api.biz.member.dto.JoinDto;
import com.parking.smart.sp_member_api.biz.member.dto.MemberDto;
import com.parking.smart.sp_member_api.biz.member.entity.Role;
import com.parking.smart.sp_member_api.biz.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MemberService {

    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public MemberDto findById(String memberId) {
        var optional = memberRepository.findByMemberId(memberId);
        if (optional.isEmpty()) throw new AlertException("유효하지 않은 계정입니다.");
        var member = optional.get();
        var memberDto = memberMapper.from(member);
        memberDto.setRole(member.getRole());
        return memberDto;
    }

    @Transactional
    public void joinMember(JoinDto joinDto) {
        checkDuplicateId(joinDto.getMemberId());
        var member = memberMapper.from(joinDto);
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setRole(Role.USER);
        memberRepository.save(member);
    }

    public void checkDuplicateId(String memberId) {
        if (memberRepository.existsByMemberId(memberId)) {
            throw new AlertException("이미 사용중인 계정입니다.");
        }
    }
}
