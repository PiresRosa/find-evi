package com.dnd.spaced.domain.word.domain.repository.dto.response;

import com.dnd.spaced.domain.word.domain.Pronunciation;

public record WordSearchDto(
        Long id,
        String name,
        Pronunciation pronunciation,
        String meaning,
        String category,
        Integer viewCount,
        Integer commentCount,
        Boolean isMarked
) {}
