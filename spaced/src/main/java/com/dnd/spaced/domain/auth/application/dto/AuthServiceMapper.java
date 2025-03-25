package com.dnd.spaced.domain.auth.application.dto;

import com.dnd.spaced.domain.auth.application.dto.response.TokenDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthServiceMapper {

    public static TokenDto of(String accessToken, String refreshToken) {
        return new TokenDto(accessToken, refreshToken);
    }
}
