package com.dnd.spaced.domain.skill.presentation.dto.response;

import com.dnd.spaced.domain.skill.domain.Category;
import lombok.Builder;

import java.util.Map;

@Builder
public record SkillTotalResponse(
        int totalAvgResponse,
        Map<Category,SkillTotalScoreResponse> skillTotalScoreResponse
) {
}
