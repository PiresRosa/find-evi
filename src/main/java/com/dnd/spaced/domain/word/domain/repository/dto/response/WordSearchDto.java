package com.dnd.spaced.domain.word.domain.repository.dto.response;

import com.dnd.spaced.domain.word.domain.Category;
import com.dnd.spaced.domain.word.domain.Pronunciation;

public record WordSearchDto(
        Long id,
        String name,
        Pronunciation pronunciation,
        String meaning,
        Category category,
        Integer viewCount,
        Integer commentCount,
        Boolean isBookmarked
) {}
