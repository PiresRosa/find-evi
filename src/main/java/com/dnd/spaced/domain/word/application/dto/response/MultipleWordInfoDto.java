package com.dnd.spaced.domain.word.application.dto.response;

import com.dnd.spaced.domain.word.domain.Word;
import java.time.LocalDateTime;

public record MultipleWordInfoDto(
        Long id,
        String name,
        PronunciationInfoDto pronunciationInfo,
        String meaning,
        String category,
        int viewCount,
        String example,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static MultipleWordInfoDto from(Word word) {
        return new MultipleWordInfoDto(
                word.getId(),
                word.getName(),
                new PronunciationInfoDto(word.getPronunciation().getEnglish()),
                word.getMeaning(),
                word.getCategory().getName(),
                word.getViewCount(),
                word.getExamples().toString(),
                word.getCreatedAt(),
                word.getUpdatedAt()
        );
    }

    public record PronunciationInfoDto(String english) {
    }
}
