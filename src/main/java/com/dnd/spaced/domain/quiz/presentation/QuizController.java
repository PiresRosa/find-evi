package com.dnd.spaced.domain.quiz.presentation;

import com.dnd.spaced.domain.quiz.application.QuizService;
import com.dnd.spaced.domain.quiz.application.dto.request.QuizRequestDto;
import com.dnd.spaced.domain.quiz.application.dto.response.QuizResponseDto;
import com.dnd.spaced.domain.quiz.domain.QuizResult;
import com.dnd.spaced.domain.quiz.presentation.dto.request.QuizRequest;
import com.dnd.spaced.domain.quiz.presentation.dto.response.QuizResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/learnings")
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/tests")
    public ResponseEntity<QuizResponse> createQuiz(@RequestBody QuizRequest requestDto) {
        QuizRequestDto request = new QuizRequestDto(requestDto.categoryName(), List.of()); // Assumes no answers needed for creation
        QuizResponseDto quizResponse = quizService.generateQuiz(request);
        if (quizResponse.questions().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        URI location = URI.create("/learnings/tests/" + quizResponse.quizId());
        return ResponseEntity.created(location).body(new QuizResponse(quizResponse.quizId(), quizResponse.questions()));
    }

    @GetMapping("/tests/{id}")
    public ResponseEntity<QuizResponse> getQuiz(@PathVariable Long id) {
        QuizResponseDto quizResponse = quizService.getQuiz(id);
        return ResponseEntity.ok(new QuizResponse(quizResponse.quizId(), quizResponse.questions()));
    }

    @PostMapping("/tests/{id}")
    public ResponseEntity<List<QuizResult>> submitQuiz(@PathVariable Long id,
                                                       @RequestBody QuizRequest requestDto) {
        QuizRequestDto request = new QuizRequestDto(requestDto.categoryName(), requestDto.answerIds());
        List<QuizResult> results = quizService.submitAnswers(id, request);
        return ResponseEntity.ok(results);
    }
}
