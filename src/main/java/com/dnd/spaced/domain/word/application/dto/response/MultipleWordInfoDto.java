package com.dnd.spaced.domain.word.application.dto.response;

import com.dnd.spaced.domain.word.domain.repository.dto.response.WordInfoWithBookmarkDto;
import java.time.LocalDateTime;

public record MultipleWordInfoDto(
        Long id,
        String name,
        PronunciationInfoDto pronunciationInfo,
        String meaning,
        String category,
        int viewCount,
        String example,
        boolean isMarked,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static MultipleWordInfoDto from(WordInfoWithBookmarkDto dto) {
        return new MultipleWordInfoDto(
                dto.wordId(),
                dto.name(),
                new PronunciationInfoDto(dto.pronunciation().getEnglish()),
                dto.meaning(),
                dto.category().getName(),
                dto.viewCount(),
                dto.example(),
                dto.bookmarkId() != null,
                dto.createdAt(),
                dto.updatedAt()
        );
    }

    public record PronunciationInfoDto(String english) {
    }
}
