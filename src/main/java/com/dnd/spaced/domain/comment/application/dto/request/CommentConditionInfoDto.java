package com.dnd.spaced.domain.comment.application.dto.request;

import org.springframework.data.domain.Pageable;

public record CommentConditionInfoDto(
        Long wordId,
        String email,
        Long lastCommentId,
        Integer lastLikeCount,
        Pageable pageable
) {
}
