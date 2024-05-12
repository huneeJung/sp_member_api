package com.parking.smart.sp_member_api.config.security.jwt;


import com.parking.smart.sp_member_api.config.security.jwt.dto.LogoutRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;
import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@ToString
public class OnUserLogoutSuccessEvent extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String memberId;
    private final String token;
    private final transient LogoutRequest logoutRequest;
    private final Date eventTime;

    public OnUserLogoutSuccessEvent(String memberId, String token, LogoutRequest logoutRequest) {
        super(memberId);
        this.memberId = memberId;
        this.token = token;
        this.logoutRequest = logoutRequest;
        this.eventTime = Date.from(Instant.now());
    }

}
