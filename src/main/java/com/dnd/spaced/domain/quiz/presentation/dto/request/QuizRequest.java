package com.dnd.spaced.domain.quiz.presentation.dto.request;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record QuizRequest(

        @NotNull
        @Schema(description = "용어 카테고리", allowableValues = {"개발", "디자인", "비즈니스"}, nullable = false)
        String categoryName,

        @NotEmpty
        @Schema(description = "정답 ID", nullable = false)
        List<Long> answerIds
) {
}
