package com.dnd.spaced.domain.bookmark.domain.repository;

import static com.dnd.spaced.domain.bookmark.domain.QBookmark.bookmark;
import static com.dnd.spaced.domain.word.domain.QWord.word;

import com.dnd.spaced.domain.bookmark.domain.Bookmark;
import com.dnd.spaced.domain.bookmark.domain.repository.dto.request.BookmarkConditionDto;
import com.dnd.spaced.domain.bookmark.domain.repository.dto.response.BookmarkWordDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuerydslBookmarkRepository implements BookmarkRepository {

    private final JPAQueryFactory queryFactory;
    private final BookmarkCrudRepository bookmarkCrudRepository;

    @Override
    public Bookmark save(Bookmark bookmark) {
        return bookmarkCrudRepository.save(bookmark);
    }

    @Override
    public void delete(Bookmark bookmark) {
        bookmarkCrudRepository.delete(bookmark);
    }

    @Override
    public Optional<Bookmark> findBy(Long accountId, Long wordId) {
        Bookmark result = queryFactory.selectFrom(bookmark)
                                        .where(
                                                bookmark.accountId.eq(accountId),
                                                bookmark.wordId.eq(wordId)
                                        )
                                        .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<BookmarkWordDto> findAllBy(BookmarkConditionDto dto) {
        return queryFactory.select(
                                   Projections.constructor(
                                           BookmarkWordDto.class,
                                           word.id,
                                           word.name,
                                           word.pronunciation,
                                           word.meaning,
                                           word.category,
                                           word.viewCount,
                                           word.examples,
                                           word.createdAt,
                                           word.updatedAt,
                                           bookmark.id
                                   )
                           )
                           .from(word)
                           .leftJoin(bookmark).on(word.id.eq(bookmark.wordId), bookmark.accountId.eq(dto.accountId()))
                           .where(lastBookmarkIdLt(dto.lastBookmarkId()))
                           .orderBy(bookmark.id.desc())
                           .fetch();
    }

    private BooleanExpression lastBookmarkIdLt(Long lastBookmarkId) {
        if (lastBookmarkId == null) {
            return null;
        }

        return bookmark.id.lt(lastBookmarkId);
    }
}
