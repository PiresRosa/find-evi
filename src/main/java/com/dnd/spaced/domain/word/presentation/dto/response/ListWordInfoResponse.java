package com.dnd.spaced.domain.word.presentation.dto.response;

import com.dnd.spaced.domain.word.application.dto.response.ListWordInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Collections;
import java.util.List;

public record ListWordInfoResponse(

        @Schema(description = "용어 정보")
        List<WordInfoResponse> words,

        @Schema(description = "마지막으로 조회한 용어 이름", nullable = true)
        String lastWordName
) {

    public static ListWordInfoResponse from(List<ListWordInfoDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new ListWordInfoResponse(Collections.emptyList(), null);
        }

        List<WordInfoResponse> words = dtos.stream()
                .map(WordInfoResponse::from)
                .toList();

        return new ListWordInfoResponse(words, words.get(words.size() - 1).name());
    }

    private record WordInfoResponse(
            @Schema(description = "용어 ID")
            Long id,

            @Schema(description = "용어 이름")
            String name,

            @Schema(description = "용어 뜻")
            String meaning,

            @Schema(description = "용어 카테고리", allowableValues = {"개발", "비즈니스", "디자인"})
            String category,

            @Schema(description = "조회수")
            int viewCount
    ) {

        public static WordInfoResponse from(ListWordInfoDto dto) {
            return new WordInfoResponse(
                    dto.id(),
                    dto.name(),
                    dto.meaning(),
                    dto.category(),
                    dto.viewCount()
            );
        }
    }
}
