package com.dnd.spaced.domain.skill.presentation.dto.request;

import com.dnd.spaced.domain.skill.domain.Category;

public record SkillRequest(
        Long correctCount,
        Category category
) {
}
