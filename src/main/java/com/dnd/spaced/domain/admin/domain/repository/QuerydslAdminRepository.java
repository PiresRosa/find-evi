package com.dnd.spaced.domain.admin.domain.repository;

import com.dnd.spaced.domain.admin.domain.repository.dto.request.AdminWordConditionDto;
import com.dnd.spaced.domain.word.domain.Category;
import com.dnd.spaced.domain.word.domain.Word;
import com.dnd.spaced.domain.word.domain.exception.InvalidCategoryException;
import com.dnd.spaced.domain.word.domain.repository.exception.UnsupportedWordSortConditionException;
import com.dnd.spaced.global.repository.OrderByNull;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dnd.spaced.domain.word.domain.QWord.word;

@Repository
@RequiredArgsConstructor
public class QuerydslAdminRepository implements AdminRepository{

    private static final String SORT_CONDITION = "name";
    private static final String IGNORE_CATEGORY = "전체";

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Word> findAllBy(AdminWordConditionDto adminWordConditionDto) {
        Sort.Order order = findOrder(adminWordConditionDto.pageable());

        return queryFactory.selectFrom(word)
                .where(
                        categoryEq(adminWordConditionDto.categoryName()),
                        lastWordNameLt(adminWordConditionDto.lastWordName())
                )
                .orderBy(orderByName(order))
                .limit(adminWordConditionDto.pageable().getPageSize())
                .fetch();
    }

    private BooleanExpression lastWordNameLt(String lastWordName) {
        if (lastWordName == null) {
            return null;
        }

        return word.name.gt(lastWordName);
    }

    @SuppressWarnings("unchecked")
    private OrderSpecifier<String> orderByName(Sort.Order order) {
        if (order == null) {
            return OrderByNull.DEFAULT;
        }

        validateSortCondition(order);

        return calculateOrderSpecifier(order);
    }

    private void validateSortCondition(Sort.Order order) {
        String sortCondition = order.getProperty();

        if (!SORT_CONDITION.equals(sortCondition)) {
            throw new UnsupportedWordSortConditionException();
        }
    }

    private OrderSpecifier<String> calculateOrderSpecifier(Sort.Order order) {
        String sortOrder = order.getDirection().toString();

        if (sortOrder == null || Sort.Direction.ASC.name().equalsIgnoreCase(sortOrder)) {
            return word.name.asc();
        }

        return word.name.desc();
    }

    private BooleanExpression categoryEq(String category) {
        if (category == null || IGNORE_CATEGORY.equals(category)) {
            return null;
        }

        try {
            Category categoryEnum = Category.findBy(category);

            return word.category.eq(categoryEnum);
        } catch (InvalidCategoryException e) {
            return null;
        }
    }

    private Sort.Order findOrder(Pageable pageable) {
        return pageable.getSort()
                .get()
                .findAny()
                .orElse(null);
    }
}
