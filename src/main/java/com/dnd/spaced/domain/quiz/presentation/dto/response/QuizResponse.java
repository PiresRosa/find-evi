package com.dnd.spaced.domain.quiz.presentation.dto.response;

import com.dnd.spaced.domain.quiz.domain.QuizQuestion;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record QuizResponse(

        @Schema(description = "퀴즈 ID")
        Long quizId,

        @Schema(description = "용어 퀴즈 목록")
        List<QuizQuestion> questions
) {
}
