package com.dnd.spaced.domain.word.presentation.dto.response;

import com.dnd.spaced.domain.word.domain.Category;
import com.dnd.spaced.domain.word.domain.Pronunciation;

public record WordSearchInfoResponse(
        Long id,
        String name,
        Pronunciation pronunciation,
        String meaning,
        Category category,
        Integer viewCount,
        Integer commentCount,
        Boolean isBookmarked
) {
}
