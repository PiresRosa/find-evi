package com.dnd.spaced.domain.word.application.dto.response;

import com.dnd.spaced.domain.word.domain.repository.dto.response.WordInfoWithBookmarkDto;
import java.time.LocalDateTime;
import java.util.List;

public record DetailWordInfoDto(
        Long id,
        String name,
        PronunciationInfoDto pronunciationInfo,
        String meaning,
        String category,
        int viewCount,
        int commentCount,
        int bookmarkCount,
        List<String> examples,
        boolean isMarked,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static DetailWordInfoDto from(WordInfoWithBookmarkDto dto) {
        return new DetailWordInfoDto(
                dto.wordId(),
                dto.name(),
                new PronunciationInfoDto(dto.pronunciation().getEnglish()),
                dto.meaning(),
                dto.category().getName(),
                dto.viewCount() + 1,
                dto.commentCount(),
                dto.bookmarkCount(),
                dto.examples(),
                dto.bookmarkId() != null,
                dto.createdAt(),
                dto.updatedAt()
        );
    }

    public record PronunciationInfoDto(String english) {
    }
}
