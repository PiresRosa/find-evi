package com.dnd.spaced.domain.admin.presentation.dto.response;

import com.dnd.spaced.domain.word.domain.Pronunciation;
import io.swagger.v3.oas.annotations.media.Schema;

public record AdminWordResponse(

        @Schema(description = "용어 ID")
        Long id,

        @Schema(description = "용어")
        String name,

        @Schema(description = "발음")
        Pronunciation pronunciation,

        @Schema(description = "뜻")
        String meaning,

        @Schema(
                description = "용어 카테고리",
                allowableValues = {"전체 실무", "디자인", "개발", "비즈니스"},
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String category,

        @Schema(description = "예문")
        String example
) {}
