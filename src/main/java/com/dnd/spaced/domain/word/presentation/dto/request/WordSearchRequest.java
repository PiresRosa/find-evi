package com.dnd.spaced.domain.word.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record WordSearchRequest(

        @Schema(description = "검색할 용어 이름", requiredMode = RequiredMode.NOT_REQUIRED)
        String name,

        @Schema(description = "검색할 용어 발음", requiredMode = RequiredMode.NOT_REQUIRED)
        String pronunciation,

        @Schema(description = "마지막으로 조회한 용어 이름", requiredMode = RequiredMode.NOT_REQUIRED)
        String lastWordName,

        @Schema(
                description = "검색할 용어 카테고리",
                allowableValues = {"전체", "디자인", "개발", "비즈니스"},
                requiredMode = RequiredMode.NOT_REQUIRED
        )
        String category
) {

    public WordSearchRequest {
        if (category == null || category.isBlank()) {
            category = "전체";
        }
    }
}
