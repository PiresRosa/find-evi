package com.dnd.spaced.domain.comment.domain.repository.util;

import static com.dnd.spaced.domain.comment.domain.QComment.comment;

import com.dnd.spaced.domain.comment.domain.repository.exception.UnsupportedCommentSortConditionException;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public enum CommentSortConditionConverter {
    LIKE_COUNT("likeCount", List.of(comment.likeCount, comment.id)),
    CREATED_AT("createdAt", List.of(comment.id))
    ;

    private final String sortCondition;
    private final List<ComparableExpressionBase<?>> expressions;

    CommentSortConditionConverter(String sortCondition, List<ComparableExpressionBase<?>> expressions) {
        this.sortCondition = sortCondition;
        this.expressions = expressions;
    }

    public static List<OrderSpecifier<?>> convert(Order order) {
        return Arrays.stream(CommentSortConditionConverter.values())
                     .filter(converter -> converter.sortCondition.equalsIgnoreCase(order.getProperty()))
                     .findAny()
                     .map(converter -> converter.convertOrderSpecifiers(order.getDirection()))
                     .orElseThrow(UnsupportedCommentSortConditionException::new);
    }

    private List<OrderSpecifier<?>> convertOrderSpecifiers(Direction direction) {
        List<OrderSpecifier<?>> result = new ArrayList<>();

        for (ComparableExpressionBase<?> expression : this.expressions) {
            result.add(applyDirection(expression, direction));
        }

        return result;
    }

    private OrderSpecifier<?> applyDirection(ComparableExpressionBase<?> expression, Direction direction) {
        if (direction.isAscending()) {
            return expression.asc();
        }

        return expression.desc();
    }
}
