package com.dnd.spaced.domain.quiz.domain.repository;

import com.dnd.spaced.domain.quiz.domain.*;
import com.dnd.spaced.domain.word.domain.Category;
import com.dnd.spaced.domain.word.domain.exception.InvalidCategoryException;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.dnd.spaced.domain.quiz.domain.QQuizQuestion.quizQuestion;
import static com.dnd.spaced.domain.quiz.domain.QQuizResult.quizResult;
import static com.dnd.spaced.domain.word.domain.QWord.word;

@Repository
@RequiredArgsConstructor
public class QuerydslQuizRepository implements QuizRepository {

    private final JPAQueryFactory queryFactory;
    private static final String IGNORE_CATEGORY = "전체";

    @Override
    public List<QuizQuestion> findQuestionsByCategory(String categoryName) {
        return queryFactory.selectFrom(quizQuestion)
                .where(categoryEq(categoryName))
                .fetch();
    }

    private BooleanExpression categoryEq(String category) {
        if (category == null || IGNORE_CATEGORY.equals(category)) {
            return null;
        }

        try {
            Category categoryEnum = Category.findBy(category);
            return quizQuestion.category.eq(categoryEnum);
        } catch (InvalidCategoryException e) {
            return null;
        }
    }
}
