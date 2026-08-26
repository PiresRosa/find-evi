package com.dnd.spaced.domain.word.domain.repository.dto.response;

public record WordSearchDto(
        Long id,
        String name,
        String pronunciationKorean,
        String pronunciationEnglish,
        String meaning,
        String category,
        Integer viewCount,
        Integer commentCount
) {
}
