package com.dnd.spaced.domain.quiz.presentation.dto.response;

import com.dnd.spaced.domain.quiz.domain.QuizQuestion;

import java.util.List;

public record QuizResponse(
        Long quizId,
        List<QuizQuestion> questions
) {
}
