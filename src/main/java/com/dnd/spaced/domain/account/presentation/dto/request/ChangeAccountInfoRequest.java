package com.dnd.spaced.domain.account.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ChangeAccountInfoRequest(@NotBlank String nickname, @NotBlank String profileImage) {
}
