package com.dnd.spaced.domain.admin.application.dto.request;

import com.dnd.spaced.domain.word.domain.Pronunciation;

public record AdminWordRequestDto(
        String name,
        String meaning,
        Pronunciation pronunciation,
        String category,
        String example
) {}
