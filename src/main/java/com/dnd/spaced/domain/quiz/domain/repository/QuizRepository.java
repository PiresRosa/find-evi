package com.dnd.spaced.domain.quiz.domain.repository;

import com.dnd.spaced.domain.quiz.domain.QuizQuestion;

import java.util.List;

public interface QuizRepository {
    List<QuizQuestion> findQuestionsByCategory(String categoryName);
}
