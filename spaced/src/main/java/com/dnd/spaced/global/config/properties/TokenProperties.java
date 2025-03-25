package com.dnd.spaced.global.config.properties;

import com.dnd.spaced.domain.auth.domain.TokenType;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("token")
public record TokenProperties(
        String accessKey,
        String refreshKey,
        int accessExpiredSeconds,
        int refreshExpiredSeconds,
        long accessExpiredMillisSeconds,
        long refreshExpiredMillisSeconds
) {

    public String findTokenKey(TokenType tokenType) {
        if (TokenType.ACCESS == tokenType) {
            return accessKey;
        }

        return refreshKey;
    }

    public Long findExpiredMillisSeconds(TokenType tokenType) {
        if (TokenType.ACCESS == tokenType) {
            return accessExpiredMillisSeconds;
        }

        return refreshExpiredMillisSeconds;
    }
}
