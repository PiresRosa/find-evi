package com.dnd.spaced.domain.quiz.presentation;

import com.dnd.spaced.domain.quiz.application.QuizService;
import com.dnd.spaced.domain.quiz.domain.QuizQuestion;
import com.dnd.spaced.domain.quiz.domain.QuizResult;
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
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/learnings")
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/tests")
    public ResponseEntity<Void> createQuiz(@RequestBody Map<String, String> request) {
        String category = request.get("testType");
        List<QuizQuestion> quiz = quizService.generateQuiz(category);

        if (quiz.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Long quizId = quizService.saveQuiz(quiz);
        URI location = URI.create("/learnings/tests/" + quizId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/tests/{id}")
    public ResponseEntity<List<QuizQuestion>> getQuiz(@PathVariable Long id) {
        List<QuizQuestion> quiz = quizService.getQuiz(id);
        return ResponseEntity.ok(quiz);
    }

    @PostMapping("/tests/{id}")
    public ResponseEntity<List<QuizResult>> submitQuiz(@PathVariable Long id,
                                                       @RequestBody List<Long> answers) {
        List<QuizResult> results = quizService.submitAnswers(id, answers);
        return ResponseEntity.ok(results);
    }
}
