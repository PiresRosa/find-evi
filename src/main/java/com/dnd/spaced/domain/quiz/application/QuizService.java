package com.dnd.spaced.domain.quiz.application;

import com.dnd.spaced.domain.quiz.application.dto.request.QuizRequestDto;
import com.dnd.spaced.domain.quiz.application.dto.response.QuizResponseDto;
import com.dnd.spaced.domain.quiz.application.exception.InvalidOptionException;
import com.dnd.spaced.domain.quiz.application.exception.NotEnoughQuestionsException;
import com.dnd.spaced.domain.quiz.application.exception.QuizNotFoundException;
import com.dnd.spaced.domain.quiz.domain.Quiz;
import com.dnd.spaced.domain.quiz.domain.QuizOption;
import com.dnd.spaced.domain.quiz.domain.QuizQuestion;
import com.dnd.spaced.domain.quiz.domain.QuizResult;
import com.dnd.spaced.domain.quiz.domain.repository.QuizCrudRepository;
import com.dnd.spaced.domain.quiz.domain.repository.QuizRepository;
import com.dnd.spaced.domain.quiz.domain.repository.QuizResultRepository;
import com.dnd.spaced.domain.word.domain.exception.InvalidCategoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuizService {

    private final QuizResultRepository quizResultRepository;
    private final QuizRepository quizRepository;
    private final QuizCrudRepository quizCrudRepository;

    public QuizResponseDto generateQuiz(QuizRequestDto requestDto) {
        QuizQuestion category = quizRepository.findByName(requestDto.categoryName())
                .orElseThrow(() -> new InvalidCategoryException());

        List<QuizQuestion> questions = quizRepository.findQuestionsByCategory(category.getCategory());
        if (questions.size() < 5) {
            throw new NotEnoughQuestionsException();
        }

        Collections.shuffle(questions);
        List<QuizQuestion> selectedQuestions = questions.stream().limit(5).collect(Collectors.toList());

        Long quizId = saveQuiz(selectedQuestions);

        return new QuizResponseDto(quizId, selectedQuestions);
    }

    private Long saveQuiz(List<QuizQuestion> questions) {
        Quiz quiz = Quiz.builder()
                .questions(questions)
                .build();

        quiz = quizCrudRepository.save(quiz);
        return quiz.getId();
    }

    public QuizResponseDto getQuiz(Long quizId) {
        Quiz quiz = quizCrudRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException());

        return new QuizResponseDto(quiz.getId(), quiz.getQuestions());
    }

    @Transactional
    public List<QuizResult> submitAnswers(Long quizId, QuizRequestDto requestDto) {
        List<QuizQuestion> questions = getQuiz(quizId).questions();
        List<QuizResult> results = new ArrayList<>();

        for (int i = 0; i < questions.size(); i++) {
            QuizQuestion question = questions.get(i);
            int finalI = i;
            QuizOption selectedOption = question.getOptions().stream()
                    .filter(option -> option.getId().equals(requestDto.answerIds().get(finalI)))
                    .findFirst()
                    .orElseThrow(() -> new InvalidOptionException());

            boolean isCorrect = selectedOption.getId().equals(question.getCorrectOption());

            QuizResult result = QuizResult.builder()
                    .quizQuestion(question)
                    .selectedOption(selectedOption)
                    .isCorrect(isCorrect)
                    .build();

            results.add(result);
        }

        return (List<QuizResult>) quizResultRepository.saveAll(results);
    }
}
