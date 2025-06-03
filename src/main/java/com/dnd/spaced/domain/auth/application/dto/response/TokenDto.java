package com.dnd.spaced.domain.auth.application.dto.response;

public record TokenDto(String accessToken, String refreshToken) {
}
