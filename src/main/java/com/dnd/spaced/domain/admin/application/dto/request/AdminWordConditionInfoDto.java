package com.dnd.spaced.domain.admin.application.dto.request;

import org.springframework.data.domain.Pageable;

public record AdminWordConditionInfoDto(
        String categoryName,
        String lastWordName,
        Pageable pageable)
{
}
