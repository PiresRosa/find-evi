package com.dnd.spaced.domain.auth.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CareerInfoRequest(@NotBlank String jobGroup, @NotBlank String company, @NotBlank String experience) {
}
