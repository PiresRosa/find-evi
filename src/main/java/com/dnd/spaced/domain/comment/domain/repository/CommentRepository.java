package com.dnd.spaced.domain.comment.domain.repository;

import com.dnd.spaced.domain.comment.domain.Comment;
import com.dnd.spaced.domain.comment.domain.repository.dto.request.CommentConditionDto;
import com.dnd.spaced.domain.comment.domain.repository.dto.request.FindCommentAllByLikedConditionDto;
import com.dnd.spaced.domain.comment.domain.repository.dto.request.FindCommentAllByWrittenConditionDto;
import com.dnd.spaced.domain.comment.domain.repository.dto.response.CommentInfoWithLikeDto;
import com.dnd.spaced.domain.comment.domain.repository.dto.response.PopularCommentInfoDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> findBy(Long id);

    void delete(Comment comment);

    List<CommentInfoWithLikeDto> findAllBy(CommentConditionDto dto);

    List<PopularCommentInfoDto> findPopularAllBy(Pageable pageable, Long accountId);

    boolean existsBy(Long commentId);

    List<Comment> findAllByLiked(FindCommentAllByLikedConditionDto dto);

    List<Comment> findAllByWritten(FindCommentAllByWrittenConditionDto dto);
}
