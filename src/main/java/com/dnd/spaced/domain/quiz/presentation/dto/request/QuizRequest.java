package com.dnd.spaced.domain.quiz.presentation.dto.request;
import java.util.List;

public record QuizRequest(
        String categoryName,
        List<Long> answerIds
) {
}
