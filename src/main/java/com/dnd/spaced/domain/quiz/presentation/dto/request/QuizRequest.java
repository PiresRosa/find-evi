package com.dnd.spaced.domain.quiz.presentation.dto.request;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record QuizRequest(

        @NotNull
        String categoryName,

        @NotEmpty
        List<Long> answerIds
) {
}
