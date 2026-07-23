package com.dnd.spaced.domain.learning.dto.response;

import java.util.List;

public record TestExplanationResponse(
        Long id,
        long correctCount,
        List<ExplanationInfoResponse> explanationInfo
) {

    public record ExplanationInfoResponse(
            Long wordId,
            boolean isCorrect,
            boolean isMarked,
            String name,
            String korean,
            String selectedOptionDescription,
            String answerOptionDescription
    ) {
    }
}
