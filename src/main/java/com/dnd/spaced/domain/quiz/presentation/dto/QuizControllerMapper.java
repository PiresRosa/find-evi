package com.dnd.spaced.domain.quiz.presentation.dto;

import com.dnd.spaced.domain.quiz.application.dto.request.QuizRequestDto;
import com.dnd.spaced.domain.quiz.application.dto.response.QuizResponseDto;
import com.dnd.spaced.domain.quiz.presentation.dto.request.QuizRequest;
import com.dnd.spaced.domain.quiz.presentation.dto.response.QuizResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class QuizControllerMapper {

    public static QuizRequestDto to(QuizRequest request) {
        return new QuizRequestDto(request.categoryName(), request.answerIds());
    }

    public static QuizResponse toResponse(QuizResponseDto dto) {
        return new QuizResponse(dto.quizId(), dto.questions());
    }
}
