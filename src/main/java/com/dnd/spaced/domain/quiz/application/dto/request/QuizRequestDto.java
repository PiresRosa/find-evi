package com.dnd.spaced.domain.quiz.application.dto.request;

import java.util.List;

public record QuizRequestDto(
        String categoryName,
        List<Long> answerIds
) {
}
