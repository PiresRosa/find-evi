package com.dnd.spaced.domain.word.application.dto.request;

import org.springframework.data.domain.Pageable;

public record SearchWordConditionInfoDto(
        String name,
        String pronunciation,
        String lastWordName,
        String category,
        Pageable pageable
) {
}
