package com.dnd.spaced.domain.quiz.presentation;

import com.dnd.spaced.domain.quiz.application.QuizService;
import com.dnd.spaced.domain.quiz.application.dto.request.QuizRequestDto;
import com.dnd.spaced.domain.quiz.application.dto.response.QuizResponseDto;
import com.dnd.spaced.domain.quiz.domain.QuizResult;
import com.dnd.spaced.domain.quiz.presentation.dto.QuizControllerMapper;
import com.dnd.spaced.domain.quiz.presentation.dto.request.QuizRequest;
import com.dnd.spaced.domain.quiz.presentation.dto.response.QuizResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/learnings")
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/tests")
    public ResponseEntity<QuizResponse> createQuiz(@Valid @RequestBody QuizRequest request) {
        QuizRequestDto requestDto = QuizControllerMapper.to(request);
        QuizResponseDto responseDto = quizService.generateQuiz(requestDto);

        return ResponseEntity.ok(QuizControllerMapper.toResponse(responseDto));
    }

    @GetMapping("/tests/{id}")
    public ResponseEntity<QuizResponse> getQuiz(@PathVariable Long id) {
        QuizResponseDto responseDto = quizService.getQuiz(id);
        return ResponseEntity.ok(QuizControllerMapper.toResponse(responseDto));
    }

    @PostMapping("/tests/{id}")
    public ResponseEntity<List<QuizResult>> submitQuiz(@PathVariable Long id,
                                                       @RequestBody QuizRequest request) {
        QuizRequestDto requestDto = QuizControllerMapper.to(request);
        List<QuizResult> results = quizService.submitAnswers(id, requestDto);
        return ResponseEntity.ok(results);
    }
}
