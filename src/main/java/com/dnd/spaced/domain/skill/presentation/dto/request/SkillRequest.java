package com.dnd.spaced.domain.skill.presentation.dto.request;

import com.dnd.spaced.domain.skill.domain.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public record SkillRequest(
        Long correctCount,
        Category category
) {
}
