package com.dnd.spaced.domain.admin.application.dto.request;

import com.dnd.spaced.domain.word.domain.Pronunciation;

import java.util.List;

public record AdminWordRequestDto(
        String name,
        String meaning,
        Pronunciation pronunciation,
        String category,
        List<String> examples
) {}
