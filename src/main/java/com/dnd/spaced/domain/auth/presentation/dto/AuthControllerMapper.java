package com.dnd.spaced.domain.auth.presentation.dto;

import com.dnd.spaced.domain.auth.application.dto.response.TokenDto;
import com.dnd.spaced.domain.auth.presentation.dto.response.TokenResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthControllerMapper {

    public static TokenResponse from(TokenDto tokenDto) {
        return new TokenResponse(tokenDto.accessToken());
    }
}
