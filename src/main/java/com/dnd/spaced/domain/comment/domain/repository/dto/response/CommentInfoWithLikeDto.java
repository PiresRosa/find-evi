package com.dnd.spaced.domain.comment.domain.repository.dto.response;

import com.dnd.spaced.domain.account.domain.CareerInfo;

import java.time.LocalDateTime;

public record CommentInfoWithLikeDto(
        Long commentId,
        Long writerId,
        Long wordId,
        String content,
        int likeCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String writerNickname,
        String writerProfileImage,
        Long likeAccountId,
        boolean isLike,
        CareerInfo careerInfo
) {
}
