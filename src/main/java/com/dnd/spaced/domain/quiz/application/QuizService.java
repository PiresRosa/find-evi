package com.dnd.spaced.domain.quiz.application;

import com.dnd.spaced.domain.quiz.application.dto.QuizServiceMapper;
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
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizCrudRepository quizCrudRepository;
    private final QuizResultRepository quizResultRepository;

    public QuizResponseDto generateQuiz(QuizRequestDto requestDto) {
        QuizQuestion category = findCategoryByName(requestDto.categoryName());
        List<QuizQuestion> questions = findQuestionsByCategory(category);
        validateQuestionCount(questions);

        List<QuizQuestion> selectedQuestions = selectRandomQuestions(questions);
        Long quizId = saveQuiz(selectedQuestions);

        return QuizServiceMapper.toResponse(quizId, selectedQuestions);
    }

    public QuizResponseDto getQuiz(Long quizId) {
        Quiz quiz = findQuizById(quizId);
        return QuizServiceMapper.toResponse(quiz.getId(), quiz.getQuestions());
    }

    @Transactional
    public List<QuizResult> submitAnswers(Long quizId, QuizRequestDto requestDto) {
        List<QuizQuestion> questions = findQuizById(quizId).getQuestions();
        return processResults(questions, requestDto);
    }

    private QuizQuestion findCategoryByName(String categoryName) {
        return quizRepository.findByName(categoryName)
                .orElseThrow(InvalidCategoryException::new);
    }

    private List<QuizQuestion> findQuestionsByCategory(QuizQuestion category) {
        return quizRepository.findQuestionsByCategory(category.getCategory());
    }

    private void validateQuestionCount(List<QuizQuestion> questions) {
        if (questions.size() < 5) {
            throw new NotEnoughQuestionsException();
        }
    }

    private List<QuizQuestion> selectRandomQuestions(List<QuizQuestion> questions) {
        Collections.shuffle(questions);
        return questions.stream().limit(5).collect(Collectors.toList());
    }

    private Long saveQuiz(List<QuizQuestion> questions) {
        Quiz quiz = Quiz.builder()
                .questions(questions)
                .build();
        quiz = quizCrudRepository.save(quiz);
        return quiz.getId();
    }

    private Quiz findQuizById(Long quizId) {
        return quizCrudRepository.findById(quizId)
                .orElseThrow(QuizNotFoundException::new);
    }

    private List<QuizResult> processResults(List<QuizQuestion> questions, QuizRequestDto requestDto) {
        List<QuizResult> results = new ArrayList<>();

        for (int i = 0; i < questions.size(); i++) {
            QuizQuestion question = questions.get(i);
            QuizOption selectedOption = findSelectedOption(question, requestDto.answerIds().get(i));
            boolean isCorrect = question.isCorrect(selectedOption);

            QuizResult result = createQuizResult(question, selectedOption, isCorrect);
            results.add(result);
        }

        return (List<QuizResult>) quizResultRepository.saveAll(results);
    }

    private QuizOption findSelectedOption(QuizQuestion question, Long answerId) {
        return question.getOptions().stream()
                .filter(option -> option.getId().equals(answerId))
                .findFirst()
                .orElseThrow(InvalidOptionException::new);
    }

    private QuizResult createQuizResult(QuizQuestion question, QuizOption selectedOption, boolean isCorrect) {
        return QuizResult.builder()
                .quizQuestion(question)
                .selectedOption(selectedOption)
                .isCorrect(isCorrect)
                .build();
    }
}
