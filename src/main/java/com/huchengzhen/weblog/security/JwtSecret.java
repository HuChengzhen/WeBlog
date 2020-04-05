package com.huchengzhen.weblog.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JwtSecret {
    @Value("${WeBlogJwtSecret:jwtSECRET}")
    private String jwtSecret;
}
