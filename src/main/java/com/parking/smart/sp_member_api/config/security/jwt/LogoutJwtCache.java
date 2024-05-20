package com.parking.smart.sp_member_api.config.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class LogoutJwtCache {

    private final ExpiringMap<String, OnUserLogoutSuccessEvent> tokenEventMap;

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${jwt.access-valid-time:3600000}")
    private long ACCESS_VALID_TIME;
    private Key key;

    // 엑세스토큰만 보관하고 리프레시 토큰은 DB에 보관
    public LogoutJwtCache() {
        // 일정 시간동안만 보관하는 캐시 맵 형태
        this.tokenEventMap = ExpiringMap.builder()
                .variableExpiration()
                .maxSize(1000)
                .expiration(ACCESS_VALID_TIME,TimeUnit.MILLISECONDS)
                .build();
    }

    @PostConstruct
    protected void init() {
        key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // TODO 로그아웃 시도시 해당 메서드 호출 이벤트로 빼보는거 괜찮을듯?
    public void markLogoutEventForToken(OnUserLogoutSuccessEvent event) {
        String token = event.getToken();
        if (tokenEventMap.containsKey(token)) {
            log.info(String.format("Log out token for user [%s] is already present in the cache", event.getMemberId()));
        } else {
            Date tokenExpiryDate = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration();
            long ttlForToken = getTtlForToken(tokenExpiryDate);
            log.info(String.format("Logout token cache set for [%s] with a TTL of [%s] seconds. Token is due expiry at [%s]", event.getMemberId(), ttlForToken, tokenExpiryDate));
            tokenEventMap.put(token, event, ttlForToken, TimeUnit.SECONDS);
        }
    }

    public OnUserLogoutSuccessEvent getLogoutEventForToken(String token) {
        return tokenEventMap.get(token);
    }

    private long getTtlForToken(Date date) {
        long secondAtExpiry = date.toInstant().getEpochSecond();
        long secondAtLogout = Instant.now().getEpochSecond();
        return Math.max(0, secondAtExpiry - secondAtLogout);
    }
}
