package com.dnd.spaced.domain.skill.presentation.dto.response;

import lombok.Builder;

@Builder
public record SkillTotalScoreResponse(
        Long totalScore,
        Long currentCount,
        Long totalCount
) {
}
