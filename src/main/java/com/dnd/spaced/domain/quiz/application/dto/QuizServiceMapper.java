package com.dnd.spaced.domain.quiz.application.dto;

import com.dnd.spaced.domain.quiz.application.dto.request.QuizRequestDto;
import com.dnd.spaced.domain.quiz.application.dto.response.QuizResponseDto;
import com.dnd.spaced.domain.quiz.domain.QuizQuestion;
import com.dnd.spaced.domain.quiz.presentation.dto.request.QuizRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class QuizServiceMapper {

    public static QuizResponseDto toResponse(Long quizId, List<QuizQuestion> questions) {
        return new QuizResponseDto(quizId, questions);
    }

    public static QuizRequestDto to(QuizRequest request) {
        return new QuizRequestDto(request.categoryName(), request.answerIds());
    }
}
