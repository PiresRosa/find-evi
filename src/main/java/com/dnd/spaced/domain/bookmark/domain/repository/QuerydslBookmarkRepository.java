package com.dnd.spaced.domain.bookmark.domain.repository;

import static com.dnd.spaced.domain.bookmark.domain.QBookmark.bookmark;

import com.dnd.spaced.domain.bookmark.domain.Bookmark;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
}
