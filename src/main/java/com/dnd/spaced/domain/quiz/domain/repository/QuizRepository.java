package com.dnd.spaced.domain.quiz.domain.repository;

import com.dnd.spaced.domain.quiz.domain.QuizQuestion;
import com.dnd.spaced.domain.quiz.domain.QuizResult;
import com.dnd.spaced.domain.word.domain.Category;

import java.util.List;
import java.util.Optional;

public interface QuizRepository {
    List<QuizQuestion> findQuestionsByCategory(Category category);
    Optional<QuizResult> findResultsByQuizId(Long quizId);
    Optional<QuizQuestion> findByName(String categoryName);
}
