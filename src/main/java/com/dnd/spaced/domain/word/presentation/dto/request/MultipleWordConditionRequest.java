package com.dnd.spaced.domain.word.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record MultipleWordConditionRequest(

        @Schema(
                description = "용어 카테고리",
                allowableValues = {"전체 실무", "디자인", "개발", "비즈니스"},
                requiredMode = RequiredMode.NOT_REQUIRED
        )
        String category,

        @Schema(description = "마지막으로 조회한 용어 이름", requiredMode = RequiredMode.NOT_REQUIRED)
        String lastWordName
) {
}
