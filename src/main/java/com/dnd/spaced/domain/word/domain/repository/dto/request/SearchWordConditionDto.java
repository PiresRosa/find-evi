package com.dnd.spaced.domain.word.domain.repository.dto.request;

import org.springframework.data.domain.Pageable;

public record SearchWordConditionDto(
        String name,
        String pronunciation,
        String lastWordName,
        String category,
        Pageable pageable
) {
}
