package com.dnd.spaced.domain.word.presentation.dto.response;

import com.dnd.spaced.domain.word.application.dto.response.WordSearchInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collections;
import java.util.List;

public record MultipleSearchWordInfoResponse(

        @Schema(description = "용어 정보")
        List<WordSearchInfoResponse> words,

        @Schema(description = "마지막으로 조회한 용어 이름", nullable = true)
        String lastWordName
) {

    public static MultipleSearchWordInfoResponse from(List<WordSearchInfoDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new MultipleSearchWordInfoResponse(Collections.emptyList(), null);
        }

        List<WordSearchInfoResponse> words = dtos.stream()
                                                 .map(WordSearchInfoResponse::from)
                                                 .toList();

        return new MultipleSearchWordInfoResponse(words, words.get(words.size() - 1).name());
    }

    private record WordSearchInfoResponse(
            @Schema(description = "용어 ID")
            Long id,

            @Schema(description = "용어 이름")
            String name,

            @Schema(description = "용어 발음 정보")
            PronunciationInfoResponse pronunciationInfo,

            @Schema(description = "용어 뜻")
            String meaning,

            @Schema(description = "용어 카테고리", allowableValues = {"개발", "비즈니스", "디자인"})
            String category,

            @Schema(description = "조회수")
            int viewCount
    ) {

        private record PronunciationInfoResponse(@Schema(description = "용어 영어 발음 기호") String english) {
        }

        public static WordSearchInfoResponse from(WordSearchInfoDto dto) {
            return new WordSearchInfoResponse(
                    dto.id(),
                    dto.name(),
                    new PronunciationInfoResponse(dto.pronunciationInfo().english()),
                    dto.meaning(),
                    dto.category(),
                    dto.viewCount()
            );
        }
    }
}
