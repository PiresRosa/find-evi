package com.dnd.spaced.domain.quiz.application.dto.response;

import com.dnd.spaced.domain.quiz.domain.QuizQuestion;

import java.util.List;

public record QuizResponseDto(
        Long quizId,
        List<QuizQuestion> questions
) {
}
