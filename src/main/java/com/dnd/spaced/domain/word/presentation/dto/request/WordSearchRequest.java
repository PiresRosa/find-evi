package com.dnd.spaced.domain.word.presentation.dto.request;

import org.springframework.data.domain.Pageable;

public record WordSearchRequest(
        String name,
        String pronunciation,
        String lastWordName,
        String category,
        Pageable pageable
) {
}
