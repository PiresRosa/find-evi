package com.dnd.spaced.domain.word.domain.repository.dto.response;

import com.dnd.spaced.domain.word.domain.Category;
import com.dnd.spaced.domain.word.domain.Pronunciation;
import java.time.LocalDateTime;

public record WordInfoWithBookmarkDto(
        Long wordId,
        String name,
        Pronunciation pronunciation,
        String meaning,
        Category category,
        String example,
        int viewCount,
        Long bookmarkId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

}
