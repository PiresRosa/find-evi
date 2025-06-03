package com.dnd.spaced.domain.auth.infrastructure;

import com.dnd.spaced.domain.auth.domain.TokenDecoder;
import com.dnd.spaced.domain.auth.domain.TokenType;
import com.dnd.spaced.domain.auth.infrastructure.exception.InvalidTokenException;
import com.dnd.spaced.global.config.properties.TokenProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtDecoder implements TokenDecoder {

    public static final String CLAIM_EMAIL = "email";
    public static final String CLAIM_ROLE = "role";

    private static final String BEARER_TOKEN_PREFIX = "Bearer ";
    private static final int BEARER_END_INDEX = 7;

    private final TokenProperties tokenProperties;

    @Override
    public Optional<PrivateClaims> decode(TokenType tokenType, String token) {
        validateBearerToken(token);

        return this.parse(tokenType, token)
                   .map(this::convert);
    }

    private void validateBearerToken(String token) {
        try {
            String tokenType = token.substring(0, BEARER_END_INDEX);

            validateTokenType(tokenType);
        } catch (StringIndexOutOfBoundsException | NullPointerException ex) {
            throw new InvalidTokenException();
        }
    }

    private void validateTokenType(String tokenType) {
        if (!BEARER_TOKEN_PREFIX.equals(tokenType)) {
            throw new InvalidTokenException();
        }
    }

    private Optional<Claims> parse(TokenType tokenType, String token) {
        String key = tokenProperties.findTokenKey(tokenType);

        try {
            return Optional.of(
                    Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)))
                        .build()
                        .parseClaimsJws(findPureToken(token))
                        .getBody()
            );
        } catch (JwtException ignored) {
            return Optional.empty();
        }
    }

    private String findPureToken(String token) {
        return token.substring(BEARER_TOKEN_PREFIX.length());
    }

    private PrivateClaims convert(Claims claims) {
        return new PrivateClaims(claims.get(CLAIM_EMAIL, String.class), claims.get(CLAIM_ROLE, String.class));
    }
}
