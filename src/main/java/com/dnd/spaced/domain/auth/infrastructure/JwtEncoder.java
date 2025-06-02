package com.dnd.spaced.domain.auth.infrastructure;

import com.dnd.spaced.domain.auth.domain.TokenEncoder;
import com.dnd.spaced.domain.auth.domain.TokenType;
import com.dnd.spaced.global.config.properties.TokenProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtEncoder implements TokenEncoder {

    private static final String BEARER_TOKEN_PREFIX = "Bearer ";

    private final TokenProperties tokenProperties;

    @Override
    public String encode(LocalDateTime publishTime, TokenType tokenType, Map<String, Object> attributes) {
        Date targetDate = convertDate(publishTime);
        String key = tokenProperties.findTokenKey(tokenType);
        Long expiredMillisSeconds = tokenProperties.findExpiredMillisSeconds(tokenType);

        return BEARER_TOKEN_PREFIX + Jwts.builder()
                                         .setIssuedAt(targetDate)
                                         .setExpiration(new Date(targetDate.getTime() + expiredMillisSeconds))
                                         .addClaims(attributes)
                                         .signWith(
                                                 Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)),
                                                 SignatureAlgorithm.HS256
                                         )
                                         .compact();
    }

    private Date convertDate(LocalDateTime target) {
        Instant targetInstant = target.atZone(ZoneId.systemDefault())
                                      .toInstant();

        return Date.from(targetInstant);
    }
}
