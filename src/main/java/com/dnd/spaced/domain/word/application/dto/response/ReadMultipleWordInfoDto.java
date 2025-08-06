package com.dnd.spaced.domain.word.application.dto.response;

import com.dnd.spaced.domain.word.domain.repository.dto.response.WordWithBookmarkDto;
import java.time.LocalDateTime;

public record ReadMultipleWordInfoDto(
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

    public static ReadMultipleWordInfoDto from(WordWithBookmarkDto dto) {
        return new ReadMultipleWordInfoDto(
                dto.wordId(),
                dto.name(),
                new PronunciationInfoDto(dto.pronunciation().getKorean(), dto.pronunciation().getEnglish()),
                dto.meaning(),
                dto.category().getName(),
                dto.viewCount(),
                dto.example(),
                dto.bookmarkId() != null,
                dto.createdAt(),
                dto.updatedAt()
        );
    }

    public record PronunciationInfoDto(String korean, String english) {
    }
}
