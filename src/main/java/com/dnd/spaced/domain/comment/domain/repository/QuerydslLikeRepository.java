package com.dnd.spaced.domain.comment.domain.repository;

import static com.dnd.spaced.domain.comment.domain.QLike.like;

import com.dnd.spaced.domain.comment.domain.Like;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuerydslLikeRepository implements LikeRepository {

    private final JPAQueryFactory queryFactory;
    private final LikeCrudRepository likeCrudRepository;

    @Override
    public void save(Like like) {
        likeCrudRepository.save(like);
    }

    @Override
    public void delete(Like like) {
        likeCrudRepository.delete(like);
    }

    @Override
    public Optional<Like> findBy(Long accountId, Long commentId) {
        Like result = queryFactory.selectFrom(like)
                                .where(
                                        like.accountId.eq(accountId),
                                        like.commentId.eq(commentId)
                                )
                                .fetchOne();

        return Optional.ofNullable(result);
    }
}
