package com.dnd.spaced.domain.word.presentation.dto.response;

import com.dnd.spaced.domain.word.application.dto.response.MultipleWordInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collections;
import java.util.List;

public record MultipleWordInfoResponse(

        @Schema(description = "용어 정보")
        List<WordInfoResponse> words,

        @Schema(description = "마지막으로 조회한 용어 이름", nullable = true)
        String lastWordName
) {

    public static MultipleWordInfoResponse from(List<MultipleWordInfoDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new MultipleWordInfoResponse(Collections.emptyList(), null);
        }

        List<WordInfoResponse> words = dtos.stream()
                                           .map(WordInfoResponse::from)
                                           .toList();

        return new MultipleWordInfoResponse(words, words.get(words.size() - 1).name());
    }

    private record WordInfoResponse(
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

        public static WordInfoResponse from(MultipleWordInfoDto dto) {
            return new WordInfoResponse(
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
