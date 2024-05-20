package com.parking.smart.sp_member_api.config.security.jwt;

import com.parking.smart.sp_member_api.biz.common.exception.AlertException;
import com.parking.smart.sp_member_api.config.security.jwt.dto.AuthInfo;
import com.parking.smart.sp_member_api.config.security.jwt.dto.JwtToken;
import com.parking.smart.sp_member_api.config.security.jwt.dto.TokenInfo;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.lang.Maps;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final LogoutJwtCache logoutJwtCache;
    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    // Default 1시간
    @Value("${jwt.access-valid-time:3600000}")
    private long ACCESS_VALID_TIME;
    // Default 30일
    @Value("${jwt.refresh-valid-time:2592000000}")
    private long REFRESH_VALID_TIME;

    private Key key;

    @PostConstruct
    protected void init() {
        key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public Date accessExpiration(Date issuedAt) {
        var exp = issuedAt.getTime() + ACCESS_VALID_TIME;
        return new Date(exp);
    }

    public Date refreshExpiration(Date issuedAt) {
        var exp = issuedAt.getTime() + REFRESH_VALID_TIME;
        return new Date(exp);
    }

    public JwtToken issue(TokenInfo member) {
        var jwtToken = new JwtToken();
        var issuedAt = member.getIssueDate();

        var bearerToken = Jwts.builder()
                // jwt 토큰의 sub 필드는 토큰의 주체를 나타내는 식별자 역할
                .setSubject(member.getMemberId())
                .claim("member", member)
                .setIssuedAt(issuedAt)
                .setExpiration(accessExpiration(issuedAt))
                .signWith(key)
                .compact();

        var refreshToken = Jwts.builder()
                .setSubject("refreshToken")
                .claim("memberId", member.getMemberId())
                .setIssuedAt(issuedAt)
                .setExpiration(refreshExpiration(issuedAt))
                .signWith(key)
                .compact();

        jwtToken.setBearerToken(bearerToken);
        jwtToken.setRefreshToken(refreshToken);

        return jwtToken;
    }

    public AuthInfo validate(String token, String refreshToken) {
        var authInfo = new AuthInfo();
        try {
            if (token != null) {
                var claims = Jwts.parserBuilder()
                        // 토큰 내에 Json 형태의 member 필드 데이터 - TokenInfo 객체 형식으로 역직렬화 수행
                        .deserializeJsonWith(new JacksonDeserializer(Maps.of("member", TokenInfo.class).build()))
                        .setSigningKey(key).build()
                        .parseClaimsJws(token)
                        .getBody();
                var tokenInfo = (TokenInfo) claims.get("member");
                authInfo.setTokenInfo(tokenInfo);
                authInfo.setValid(true);
                validateTokenIsNotForALoggedOut(token);
            } else {
                var claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken).getBody();
                var memberId = (String) claims.get("memberId");
                var tokenInfo = new TokenInfo();
                tokenInfo.setMemberId(memberId);
                authInfo.setTokenInfo(tokenInfo);
                authInfo.setValid(true);
                validateTokenIsNotForALoggedOut(refreshToken);
            }
        } catch (ExpiredJwtException e) {
            log.warn("# Token expired - {}", e.getMessage());
            throw new JwtException(e.getMessage());
        } catch (SignatureException e) {
            log.warn("# Wrong signature - {}", e.getMessage());
            throw new JwtException(e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("# no token : cannot be empty - {}", e.getMessage());
            throw new JwtException(e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("# Wrong token - {}", e.getMessage());
            throw new JwtException(e.getMessage());
        } catch (Exception e) {
            log.warn("# JWT token Parse Exception - {}", e.getMessage());
            throw new JwtException(e.getMessage());
        }
        return authInfo;
    }

    private void validateTokenIsNotForALoggedOut(String token) {
        var previouslyLoggedOutEvent = logoutJwtCache.getLogoutEventForToken(token);
        if (previouslyLoggedOutEvent != null) {
            var logoutEventDate = previouslyLoggedOutEvent.getEventTime();
            var errorMessage = String.format("이미 로그아웃된 토큰입니다. [%s]", logoutEventDate);
            throw new AlertException(errorMessage);
        }
    }

}
