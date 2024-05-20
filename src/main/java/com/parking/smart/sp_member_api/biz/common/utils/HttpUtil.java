package com.parking.smart.sp_member_api.biz.common.utils;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpUtil {

    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    public static String getClientIpAddress(){
        if(RequestContextHolder.getRequestAttributes() == null){
            throw new RuntimeException("시스템 오류가 발생하였습니다.");
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        for(String header: IP_HEADER_CANDIDATES){
            String ipList = request.getHeader(header);
            if(StringUtils.isNotEmpty(ipList) && !ipList.equalsIgnoreCase("unknown")){
                return ipList.split(",")[0];
            }
        }
        return request.getRemoteAddr();
    }

}
