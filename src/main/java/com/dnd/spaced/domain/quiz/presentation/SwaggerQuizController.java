package com.dnd.spaced.domain.quiz.presentation;

import com.dnd.spaced.domain.quiz.domain.QuizResult;
import com.dnd.spaced.domain.quiz.presentation.dto.request.QuizRequest;
import com.dnd.spaced.domain.quiz.presentation.dto.response.QuizResponse;
import com.dnd.spaced.global.docs.annotation.ExceptionSpec;
import com.dnd.spaced.global.docs.annotation.ExcludeCommonHeaderSpec;
import com.dnd.spaced.global.docs.annotation.NotRequiredCommonHeaderSpec;
import com.dnd.spaced.global.exception.ExceptionCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "퀴즈 관련 API", description = "용어 테스트 생성, 용어 테스트 시작, 용어 테스트 정답 제출")
public interface SwaggerQuizController {

    @ExcludeCommonHeaderSpec
    @Operation(summary = "용어 테스트 생성", description = "용어 테스트를 생성합니다.")
    @ApiResponse(responseCode = "200", description = "OK")
    @ExceptionSpec(values = {ExceptionCode.NOT_ENOUGH_QUESTIONS_FOR_CATEGORY})
    ResponseEntity<QuizResponse> createQuiz(
            @RequestBody @Valid QuizRequest request
    );

    @NotRequiredCommonHeaderSpec
    @Operation(summary = "용어 테스트 시작", description = "용어 테스트를 시작하고 페이지를 이동합니다.")
    @ApiResponse(responseCode = "200", description = "OK")
    @ExceptionSpec(values = {ExceptionCode.QUIZ_NOT_FOUND})
    ResponseEntity<QuizResponse> getQuiz(
            @Parameter(description = "용어 테스트 ID") @PathVariable Long id
    );

    @NotRequiredCommonHeaderSpec
    @Operation(summary = "용어 테스트 정답 제출", description = "용어 테스트의 정답을 제출합니다.")
    @ApiResponse(responseCode = "200", description = "OK")
    @ExceptionSpec(values = {ExceptionCode.QUIZ_NOT_FOUND})
    ResponseEntity<List<QuizResult>> submitQuiz(
            @Parameter(description = "용어 테스트 ID") @PathVariable Long id,
            @RequestBody @Valid QuizRequest request
    );
}
