package com.dnd.spaced.domain.comment.domain.repository.dto;

import com.dnd.spaced.domain.comment.domain.repository.dto.request.CommentConditionDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommentRepositoryMapper {

    public static CommentConditionDto to(Long lastCommentId, int lastLikeCount, Pageable pageable) {
        return new CommentConditionDto(lastCommentId, lastLikeCount, pageable);
    }
}
