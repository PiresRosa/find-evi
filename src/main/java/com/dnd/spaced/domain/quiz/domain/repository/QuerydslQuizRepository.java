package com.dnd.spaced.domain.quiz.domain.repository;

import com.dnd.spaced.domain.quiz.domain.*;
import com.dnd.spaced.domain.word.domain.Category;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.dnd.spaced.domain.quiz.domain.QQuizQuestion.quizQuestion;
import static com.dnd.spaced.domain.quiz.domain.QQuizResult.quizResult;

@Repository
@RequiredArgsConstructor
public class QuerydslQuizRepository implements QuizRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<QuizQuestion> findQuestionsByCategory(Category category) {
        return queryFactory.selectFrom(quizQuestion)
                .where(quizQuestion.category.eq(category))
                .fetch();
    }

    @Override
    public Optional<QuizResult> findResultsByQuizId(Long quizId) {
        QuizResult result = queryFactory.selectFrom(quizResult)
                .where(quizResult.quizQuestion.quiz.id.eq(quizId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<QuizQuestion> findByName(String categoryName) {
        QuizQuestion result = queryFactory.selectFrom(quizQuestion)
                .where(quizQuestion.category.eq(Category.valueOf(categoryName)))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
