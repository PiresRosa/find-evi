package com.dnd.spaced.domain.word.domain.repository;

import static com.dnd.spaced.domain.bookmark.domain.QBookmark.bookmark;
import static com.dnd.spaced.domain.word.domain.QWord.word;

import com.dnd.spaced.domain.word.domain.Category;
import com.dnd.spaced.domain.word.domain.Word;
import com.dnd.spaced.domain.word.domain.exception.InvalidCategoryException;
import com.dnd.spaced.domain.word.domain.repository.dto.WordRepositoryMapper;
import com.dnd.spaced.domain.word.domain.repository.dto.request.SearchWordConditionDto;
import com.dnd.spaced.domain.word.domain.repository.dto.request.WordConditionDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordCandidateDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordInfoWithBookmarkDto;
import com.dnd.spaced.domain.word.domain.repository.exception.UnsupportedWordSortConditionException;
import com.dnd.spaced.global.repository.OrderByNull;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
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
    private static final String IGNORE_CATEGORY = "전체 실무";

    private final JPAQueryFactory queryFactory;
    private final WordCrudRepository wordCrudRepository;

    @Override
    public void save(Word word) {
        wordCrudRepository.save(word);
    }

    @Override
    public void delete(Word word) {
        wordCrudRepository.delete(word);
    }

    @Override
    public long countAllWords() {
        return queryFactory.selectFrom(word)
                .fetchCount();
    }

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
                                word.commentCount,
                                word.bookmarkCount,
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
    public List<Word> findAllBy(WordConditionDto wordConditionDto) {
        Order order = findOrder(wordConditionDto.pageable());

        return queryFactory.selectFrom(word)
                .where(
                        categoryEq(wordConditionDto.categoryName()),
                        lastWordNameLt(wordConditionDto.lastWordName())
                )
                .orderBy(orderByName(order))
                .limit(wordConditionDto.pageable().getPageSize())
                .fetch();
    }

    @Override
    public WordCandidateDto findCandidateAllBy(String target) {
        List<String> result = queryFactory.select(word.name)
                .from(word)
                .where(word.name.endsWith(target))
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

    @Override
    public void updateBookmarkCount(Long wordId, int count) {
        queryFactory.update(word)
                    .set(word.bookmarkCount, word.bookmarkCount.add(count))
                    .where(word.id.eq(wordId))
                    .execute();
    }

    @Override
    public List<Word> searchWords(SearchWordConditionDto searchWordConditionDto) {
        return queryFactory.selectFrom(word)
                .where(
                        nameContains(searchWordConditionDto.name()),
                        pronunciationContains(searchWordConditionDto.pronunciation()),
                        categoryEq(searchWordConditionDto.category()),
                        lastWordNameLt(searchWordConditionDto.lastWordName())
                )
                .orderBy(word.name.asc())
                .limit(searchWordConditionDto.pageable().getPageSize())
                .fetch();
    }

    @Override
    public List<Word> findRecentWords() {
        return queryFactory.selectFrom(word)
                .orderBy(word.createdAt.desc())
                .limit(3)
                .fetch();
    }

    @Override
    public List<Word> findRandomWords() {
        return queryFactory.selectFrom(word)
                .orderBy(Expressions.numberTemplate(Double.class, "function('RAND')").asc())
                .limit(3)
                .fetch();
    }


    private BooleanExpression lastWordNameLt(String lastWordName) {
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

        validateSortCondition(order);

        return calculateOrderSpecifier(order);
    }

    private void validateSortCondition(Order order) {
        String sortCondition = order.getProperty();

        if (!SORT_CONDITION.equals(sortCondition)) {
            throw new UnsupportedWordSortConditionException();
        }
    }

    private OrderSpecifier<String> calculateOrderSpecifier(Order order) {
        String sortOrder = order.getDirection().toString();

        if (sortOrder == null || Direction.ASC.name().equalsIgnoreCase(sortOrder)) {
            return word.name.asc();
        }

        return word.name.desc();
    }

    private BooleanExpression nameContains(String name) {
        if (name == null) {
            return null;
        }

        return word.name.containsIgnoreCase(name);
    }

    private BooleanExpression pronunciationContains(String pronunciation) {
        if (pronunciation == null) {
            return null;
        }

        return word.pronunciation.english.containsIgnoreCase(pronunciation);
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

    private Order findOrder(Pageable pageable) {
        return pageable.getSort()
                       .get()
                       .findAny()
                       .orElse(null);
    }
}
