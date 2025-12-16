package com.dnd.spaced.domain.comment.domain.repository;

import com.dnd.spaced.domain.comment.domain.Comment;
import com.dnd.spaced.domain.comment.domain.repository.dto.request.CommentConditionDto;
import com.dnd.spaced.domain.comment.domain.repository.dto.response.CommentInfoWithLikeDto;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> findBy(Long id);

    void delete(Comment comment);

    List<CommentInfoWithLikeDto> findAllBy(CommentConditionDto dto);
}
