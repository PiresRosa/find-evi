package com.dnd.spaced.domain.bookmark.domain.repository.dto.request;

import org.springframework.data.domain.Pageable;

public record BookmarkConditionDto(Long accountId, Long lastBookmarkId, Pageable pageable) {
}
