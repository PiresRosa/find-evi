package com.dnd.spaced.domain.account.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;

public record ChangeAccountInfoRequest(

        @Schema(description = "변경할 닉네임", requiredMode = RequiredMode.REQUIRED)
        @NotBlank
        String nickname,

        @Schema(description = "변경할 프로필 이미지", requiredMode = RequiredMode.REQUIRED)
        @NotBlank
        String profileImage
) {
}
