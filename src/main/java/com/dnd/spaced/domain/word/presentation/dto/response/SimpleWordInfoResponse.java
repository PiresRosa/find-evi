package com.dnd.spaced.domain.word.presentation.dto.response;

import com.dnd.spaced.domain.word.application.dto.response.ListWordInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record SimpleWordInfoResponse(
        @Schema(description = "용어 ID")
        Long id,

        @Schema(description = "용어 이름")
        String name,

        @Schema(description = "용어 뜻")
        String meaning,

        @Schema(description = "용어 카테고리", allowableValues = {"개발", "비즈니스", "디자인"})
        String category
) {

    public static SimpleWordInfoResponse from(ListWordInfoDto dto) {
        return new SimpleWordInfoResponse(
                dto.id(),
                dto.name(),
                dto.meaning(),
                dto.category()
        );
    }

    public static List<SimpleWordInfoResponse> from(List<ListWordInfoDto> dtos) {
        return dtos.stream()
                .map(SimpleWordInfoResponse::from)
                .toList();
    }
}
