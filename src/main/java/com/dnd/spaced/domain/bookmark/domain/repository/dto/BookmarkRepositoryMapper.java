package com.dnd.spaced.domain.bookmark.domain.repository.dto;

import com.dnd.spaced.domain.bookmark.domain.repository.dto.request.BookmarkConditionDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BookmarkRepositoryMapper {

    public static BookmarkConditionDto of(Long accountId, Long lastBookmarkId, Pageable pageable) {
        return new BookmarkConditionDto(accountId, lastBookmarkId, pageable);
    }
}
