package com.dnd.spaced.domain.admin.domain.repository.dto.request;

import org.springframework.data.domain.Pageable;

public record AdminWordConditionDto(
        String categoryName,
        String lastWordName,
        Pageable pageable)
{
}
