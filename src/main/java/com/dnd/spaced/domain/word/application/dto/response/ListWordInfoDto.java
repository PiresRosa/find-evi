package com.dnd.spaced.domain.word.application.dto.response;

import com.dnd.spaced.domain.word.domain.Word;

import java.time.LocalDateTime;

public record ListWordInfoDto(
        Long id,
        String name,
        String meaning,
        String category,
        int viewCount,
        String example,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static ListWordInfoDto from(Word word) {
        return new ListWordInfoDto(
                word.getId(),
                word.getName(),
                word.getMeaning(),
                word.getCategory().getName(),
                word.getViewCount(),
                word.getExample(),
                word.getCreatedAt(),
                word.getUpdatedAt()
        );
    }
}
