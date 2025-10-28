package com.dnd.spaced.domain.word.domain.repository;

import static com.dnd.spaced.domain.word.domain.QBookmark.bookmark;
import static com.dnd.spaced.domain.word.domain.QWord.word;

import com.dnd.spaced.domain.word.domain.Category;
import com.dnd.spaced.domain.word.domain.Word;
import com.dnd.spaced.domain.word.domain.repository.dto.WordRepositoryMapper;
import com.dnd.spaced.domain.word.domain.repository.dto.request.WordConditionDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordCandidateDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordInfoWithBookmarkDto;
import com.dnd.spaced.domain.word.domain.repository.exception.UnsupportedCommentSortConditionException;
import com.dnd.spaced.global.repository.OrderByNull;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuerydslWordRepository implements WordRepository {

    private static final String SORT_CONDITION = "name";
    private static final String IGNORE_CATEGORY = "전체";

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Word> findBy(Long wordId) {
        Word result = queryFactory.selectFrom(word)
                                  .where(word.id.eq(wordId))
                                  .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<WordInfoWithBookmarkDto> findWithBookmarkBy(Long wordId, Long accountId) {
        WordInfoWithBookmarkDto result = queryFactory.select(
                                                             Projections.constructor(
                                                                     WordInfoWithBookmarkDto.class,
                                                                     word.id,
                                                                     word.name,
                                                                     word.pronunciation,
                                                                     word.meaning,
                                                                     word.category,
                                                                     word.example,
                                                                     word.viewCount,
                                                                     bookmark.id,
                                                                     word.createdAt,
                                                                     word.updatedAt
                                                             )
                                                     )
                                                     .from(word)
                                                     .leftJoin(bookmark).on(
                                                            word.id.eq(bookmark.wordId),
                                                            bookmark.accountId.eq(accountId)
                                                     )
                                                     .where(word.id.eq(wordId))
                                                     .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<WordInfoWithBookmarkDto> findAllBy(WordConditionDto wordConditionDto, Long accountId) {
        Order order = findOrder(wordConditionDto.pageable());

        return queryFactory.select(
                                   Projections.constructor(
                                           WordInfoWithBookmarkDto.class,
                                           word.id,
                                           word.name,
                                           word.pronunciation,
                                           word.meaning,
                                           word.category,
                                           word.example,
                                           word.viewCount,
                                           bookmark.id,
                                           word.createdAt,
                                           word.updatedAt
                                   )
                           )
                           .from(word)
                           .leftJoin(bookmark).on(
                                word.id.eq(bookmark.wordId),
                                bookmark.accountId.eq(accountId)
                           )
                           .where(
                                   eqCategory(wordConditionDto.categoryName()),
                                   ltLastWordName(wordConditionDto.lastWordName())
                           )
                           .orderBy(orderByName(order))
                           .limit(wordConditionDto.pageable().getPageSize())
                           .fetch();
    }

    @Override
    public WordCandidateDto findCandidateAllBy(String target) {
        List<String> result = queryFactory.select(word.name)
                                          .from(word)
                                          .where(word.name.like(target + "%"))
                                          .fetch();

        return WordRepositoryMapper.to(result);
    }

    @Override
    public void updateViewCount(Long wordId) {
        queryFactory.update(word)
                    .set(word.viewCount, word.viewCount.add(1))
                    .where(word.id.eq(wordId))
                    .execute();
    }

    private BooleanExpression eqCategory(String categoryName) {
        if (IGNORE_CATEGORY.equals(categoryName)) {
            return null;
        }

        return word.category.eq(Category.findBy(categoryName));
    }

    private BooleanExpression ltLastWordName(String lastWordName) {
        if (lastWordName == null) {
            return null;
        }

        return word.name.gt(lastWordName);
    }

    @SuppressWarnings("unchecked")
    private OrderSpecifier<String> orderByName(Order order) {
        if (order == null) {
            return OrderByNull.DEFAULT;
        }

        String sortCondition = order.getProperty();

        if (!SORT_CONDITION.equals(sortCondition)) {
            throw new UnsupportedCommentSortConditionException();
        }

        String sortOrder = order.getDirection().toString();

        if (sortOrder == null || Direction.ASC.name().equalsIgnoreCase(sortOrder)) {
            return word.name.asc();
        }

        return word.name.desc();
    }

    private Order findOrder(Pageable pageable) {
        return pageable.getSort()
                       .get()
                       .findAny()
                       .orElse(null);
    }
}
