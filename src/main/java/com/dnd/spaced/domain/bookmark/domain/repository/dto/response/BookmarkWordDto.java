package com.dnd.spaced.domain.bookmark.domain.repository.dto.response;

import com.dnd.spaced.domain.word.domain.Category;
import com.dnd.spaced.domain.word.domain.Pronunciation;
import java.time.LocalDateTime;
import java.util.List;

public record BookmarkWordDto(
        Long wordId,
        String name,
        Pronunciation pronunciation,
        String meaning,
        Category category,
        int viewCount,
        List<String> examples,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long bookmarkId
) {
}
