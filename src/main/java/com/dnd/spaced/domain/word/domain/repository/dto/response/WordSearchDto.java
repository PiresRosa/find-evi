package com.dnd.spaced.domain.word.domain.repository.dto.response;

public record WordSearchDto(
        Long id,
        String name,
        Pronunciation pronunciationInfo,
        String meaning,
        String category,
        Long viewCount,
        Long commentCount,
        Boolean isMarked
) {}
