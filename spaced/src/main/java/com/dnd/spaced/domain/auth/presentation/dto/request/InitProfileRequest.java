package com.dnd.spaced.domain.auth.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record InitProfileRequest(@NotBlank String jobGroup, String company) {
}
