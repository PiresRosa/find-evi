package com.dnd.spaced.domain.auth.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record TokenResponse(@Schema(description = "액세스 토큰") String accessToken) {

}
