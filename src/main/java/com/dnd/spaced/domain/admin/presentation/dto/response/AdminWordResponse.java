package com.dnd.spaced.domain.admin.presentation.dto.response;

import com.dnd.spaced.domain.word.domain.Pronunciation;

public record AdminWordResponse(
        Long id,
        String name,
        Pronunciation pronunciation,
        String meaning,
        String category,
        String example
) {}
