package com.dnd.spaced.domain.bookmark.application.dto.response;

import com.dnd.spaced.domain.bookmark.domain.repository.dto.response.BookmarkWordDto;
import java.time.LocalDateTime;

public record BookmarkWordInfoDto(
        Long wordId,
        String name,
        PronunciationInfoDto pronunciationInfo,
        String meaning,
        String category,
        int viewCount,
        String example,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long bookmarkId
) {

    public static BookmarkWordInfoDto from(BookmarkWordDto dto) {
        return new BookmarkWordInfoDto(
                dto.wordId(),
                dto.name(),
                new PronunciationInfoDto(dto.pronunciation().getKorean(), dto.pronunciation().getEnglish()),
                dto.meaning(),
                dto.category().getName(),
                dto.viewCount(),
                dto.example(),
                dto.createdAt(),
                dto.updatedAt(),
                dto.bookmarkId()
        );
    }

    public record PronunciationInfoDto(String korean, String english) {
    }
}
