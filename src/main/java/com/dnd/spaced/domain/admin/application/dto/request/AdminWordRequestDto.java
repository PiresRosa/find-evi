package com.dnd.spaced.domain.admin.application.dto.request;

import com.dnd.spaced.domain.word.domain.Pronunciation;
import io.swagger.v3.oas.annotations.media.Schema;

public record AdminWordRequestDto(
        @Schema(description = "등록할 용어 이름")
        String name,

        @Schema(description = "등록할 용어 뜻")
        String meaning,

        @Schema(description = "등록할 용어 발음")
        Pronunciation pronunciation,

        @Schema(description = "등록할 용어의 카테고리")
        String category,

        @Schema(description = "등록할 용어 예문")
        String example,

        @Schema(description = "등록할 용어 출처")
        String resource
) {}
