package com.parking.smart.sp_member_api.biz.auth.service;

import com.parking.smart.sp_member_api.biz.auth.dto.AuthRequest;
import com.parking.smart.sp_member_api.biz.auth.dto.AuthResponse;
import com.parking.smart.sp_member_api.biz.auth.entity.LoginHistory;
import com.parking.smart.sp_member_api.biz.auth.repository.LoginHistoryRepository;
import com.parking.smart.sp_member_api.biz.auth.repository.RefreshTokenHistoryRepository;
import com.parking.smart.sp_member_api.biz.common.exception.AlertException;
import com.parking.smart.sp_member_api.biz.common.utils.HttpUtil;
import com.parking.smart.sp_member_api.biz.member.service.MemberService;
import com.parking.smart.sp_member_api.config.security.jwt.JwtTokenProvider;
import com.parking.smart.sp_member_api.config.security.jwt.dto.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AuthService {

    private final MemberService memberService;

    private final RefreshTokenHistoryRepository refreshTokenHistoryRepository;
    private final LoginHistoryRepository loginHistoryRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // TODO : Redis 활용한 중복 로그인 문제 개선 및 동일 아이피 중복 요청 제한
    public AuthResponse issueTokenInfoFrom(AuthRequest authRequest) {
        var member = memberService.findById(authRequest.getMemberId());
        if (passwordEncoder.matches(passwordEncoder.encode(authRequest.getPassword()), member.getPassword())) {
            throw new AlertException("유효하지 않은 계정입니다.");
        }

        var memberTokenInfo = TokenInfo.from(member);
        var jwtToken = jwtTokenProvider.issue(memberTokenInfo);

        // 사용자 로그인 기록 Insert
        var loginHistory = LoginHistory.builder()
                .memberId(authRequest.getMemberId())
                .ipAddress(HttpUtil.getClientIpAddress())
                .build();
        
        loginHistoryRepository.save(loginHistory);

        return AuthResponse.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .role(member.getRole().name())
                .jwtToken(jwtToken)
                .build();
    }

    /**
     * @apiNote 엑세스 토큰 만료 시 리프레시 토큰과 함께 엑세스 토큰 재발급
     */
    public AuthResponse issueTokenInfoFrom(String refreshToken) {
        var optional = refreshTokenHistoryRepository.findByRefreshToken(refreshToken);
        if (optional.isEmpty()) {
            throw new AlertException("잘못된 리프레시 토큰입니다.");
        }
        var refreshTokenHst = optional.get();
        if (refreshTokenHst.getUseYN()) {
            throw new AlertException("이미 사용된 토큰입니다.");
        }
        refreshTokenHst.setUseYN(true);

        var member = memberService.findById(refreshTokenHst.getMemberId());
        var memberTokenInfo = TokenInfo.from(member);
        var jwtToken = jwtTokenProvider.issue(memberTokenInfo);

        return AuthResponse.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .role(member.getRole().name())
                .jwtToken(jwtToken)
                .build();
    }

}
