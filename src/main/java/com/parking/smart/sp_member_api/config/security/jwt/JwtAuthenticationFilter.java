package com.parking.smart.sp_member_api.config.security.jwt;

import com.parking.smart.sp_member_api.config.security.SecurityConfig;
import com.parking.smart.sp_member_api.config.security.jwt.dto.AuthInfo;
import com.parking.smart.sp_member_api.config.security.jwt.dto.TokenInfo;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private List<AntPathRequestMatcher> publicPathList;

    @PostConstruct
    public void init() {
        publicPathList = new ArrayList<>();
        for (String url : SecurityConfig.PERMIT_ALL) {
            publicPathList.add(new AntPathRequestMatcher(url));
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (publicPathList.stream().noneMatch(url -> url.matches(request))) {
            var token = getBearerToken(request);
            var authInfo = jwtTokenProvider.validate(token, null);
            if (authInfo.isValid()) {
                var tokenInfo = authInfo.getTokenInfo();
                var authentication = new UsernamePasswordAuthenticationToken(tokenInfo, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getBearerToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}