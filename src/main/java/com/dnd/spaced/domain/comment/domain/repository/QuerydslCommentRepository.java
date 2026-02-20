package com.dnd.spaced.domain.comment.domain.repository;

import static com.dnd.spaced.domain.account.domain.QAccount.account;
import static com.dnd.spaced.domain.comment.domain.QComment.comment;
import static com.dnd.spaced.domain.comment.domain.QLike.like;
import static com.dnd.spaced.domain.word.domain.QWord.word;

import com.dnd.spaced.domain.comment.domain.Comment;
import com.dnd.spaced.domain.comment.domain.repository.dto.request.CommentConditionDto;
import com.dnd.spaced.domain.comment.domain.repository.dto.response.CommentInfoWithLikeDto;
import com.dnd.spaced.domain.comment.domain.repository.dto.response.PopularCommentInfoDto;
import com.dnd.spaced.domain.comment.domain.repository.exception.UnsupportedCommentSortConditionException;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuerydslCommentRepository implements CommentRepository {

    private static final String LIKE_COUNT_SORT_CONDITION = "likeCount";
    private static final String CREATED_AT_SORT_CONDITION = "createdAt";

    private final JPAQueryFactory queryFactory;
    private final CommentCrudRepository commentCrudRepository;

    @Override
    public Comment save(Comment comment) {
        return commentCrudRepository.save(comment);
    }

    @Override
    public Optional<Comment> findBy(Long id) {
        return commentCrudRepository.findById(id);
    }

    @Override
    public void delete(Comment comment) {
        commentCrudRepository.delete(comment);
    }

    @Override
    public List<CommentInfoWithLikeDto> findAllBy(CommentConditionDto dto) {
        return queryFactory.select(
                                   Projections.constructor(
                                           CommentInfoWithLikeDto.class,
                                           comment.id,
                                           comment.accountId,
                                           comment.wordId,
                                           comment.content,
                                           comment.likeCount,
                                           comment.createdAt,
                                           comment.updatedAt,
                                           account.nickname,
                                           account.profileImage,
                                           like.id
                                   )
                           )
                           .from(comment)
                           .join(account).on(comment.accountId.eq(account.id))
                           .leftJoin(like).on(comment.id.eq(like.commentId), like.accountId.eq(dto.accountId()))
                           .where(calculateFindAllBooleanExpressions(dto), comment.wordId.eq(dto.wordId()))
                           .orderBy(calculateFindAllOrderSpecifiers(dto.pageable()).toArray(OrderSpecifier[]::new))
                           .limit(dto.pageable().getPageSize())
                           .fetch();
    }

    @Override
    public List<PopularCommentInfoDto> findPopularAllBy(Pageable pageable, Long accountId) {
        return queryFactory.select(
                                   Projections.constructor(
                                           PopularCommentInfoDto.class,
                                           comment.id,
                                           comment.accountId,
                                           account.nickname,
                                           account.profileImage,
                                           word.id,
                                           word.name,
                                           word.category,
                                           word.pronunciation,
                                           comment.content,
                                           comment.likeCount,
                                           comment.createdAt,
                                           comment.updatedAt,
                                           like.id
                                   )
                           )
                           .from(comment)
                           .join(account).on(comment.accountId.eq(account.id))
                           .join(word).on(comment.wordId.eq(word.id))
                           .leftJoin(like).on(comment.id.eq(like.commentId), like.accountId.eq(accountId))
                           .orderBy(calculateFindPopularAllOrderSpecifier(pageable))
                           .limit(pageable.getPageSize())
                           .fetch();
    }

    private List<OrderSpecifier<?>> calculateFindAllOrderSpecifiers(Pageable pageable) {
        Order order = findOrder(pageable);

        if (LIKE_COUNT_SORT_CONDITION.equalsIgnoreCase(order.getProperty())) {
            return List.of(comment.likeCount.desc(), comment.id.desc());
        }
        if (CREATED_AT_SORT_CONDITION.equalsIgnoreCase(order.getProperty())) {
            if (order.isAscending()) {
                return List.of(comment.id.asc());
            }

            return List.of(comment.id.desc());
        }

        throw new UnsupportedCommentSortConditionException();
    }

    private BooleanExpression calculateFindAllBooleanExpressions(CommentConditionDto dto) {
        Order order = findOrder(dto.pageable());

        if (order == null) {
            return null;
        }
        if (dto.lastLikeCount() == null) {
            return ltLastCommentId(dto.lastCommentId());
        }

        String sortBy = order.getProperty();

        if (CREATED_AT_SORT_CONDITION.equals(sortBy)) {
            if (order.isAscending()) {
                return gtLastCommentId(dto.lastCommentId());
            }
            return ltLastCommentId(dto.lastCommentId());
        }

        return comment.likeCount.lt(dto.lastLikeCount())
                                .or(comment.likeCount.eq(dto.lastLikeCount())
                                                     .and(ltLastCommentId(dto.lastCommentId()))
                                );
    }

    private BooleanExpression ltLastCommentId(Long lastCommentId) {
        if (lastCommentId == null) {
            return null;
        }

        return comment.id.lt(lastCommentId);
    }

    private BooleanExpression gtLastCommentId(Long lastCommentId) {
        if (lastCommentId == null) {
            return null;
        }

        return comment.id.gt(lastCommentId);
    }

    private OrderSpecifier<?> calculateFindPopularAllOrderSpecifier(Pageable pageable) {
        Order order = findOrder(pageable);

        if (LIKE_COUNT_SORT_CONDITION.equalsIgnoreCase(order.getProperty())) {
            return comment.likeCount.desc();
        }

        throw new UnsupportedCommentSortConditionException();
    }

    private Order findOrder(Pageable pageable) {
        return pageable.getSort()
                       .get()
                       .findAny()
                       .orElse(null);
    }
}
