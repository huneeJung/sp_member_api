package com.parking.smart.sp_member_api.biz.auth.aop;

import com.parking.smart.sp_member_api.biz.auth.dto.AuthRequest;
import com.parking.smart.sp_member_api.biz.common.exception.AlertException;
import com.parking.smart.sp_member_api.biz.common.utils.HttpUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class LoginAttemptAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Before("execution(* com.parking.smart.sp_member_api.biz.auth.service.AuthService.issueTokenInfoFrom(..)) && args(authRequest)")
    public void checkLoginAttempt(AuthRequest authRequest) {
        String key = "loginAttempt:" + HttpUtil.getClientIpAddress();
        String value = redisTemplate.opsForValue().get(key);
        int attempts = value != null ? Integer.parseInt(value) : 0;

        if (attempts >= 5) {
            throw new AlertException("로그인 시도 횟수 초과");
        }

        redisTemplate.opsForValue().increment(key, 1);
        redisTemplate.expire(key, 1, TimeUnit.HOURS); // 1시간 동안 유효
    }
}