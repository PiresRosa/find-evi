package com.dnd.spaced.domain.comment.domain.repository.dto.response;

import java.time.LocalDateTime;

public record CommentInfoWithLikeDto(
        Long commentId,
        Long accountId,
        Long wordId,
        String content,
        int likeCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long likeAccountId
) {
}
