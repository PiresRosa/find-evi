package com.dnd.spaced.domain.comment.application.dto.response;

import com.dnd.spaced.domain.account.domain.Account;
import com.dnd.spaced.domain.comment.domain.Comment;
import java.time.LocalDateTime;

public record LikedCommentDto(
        Long commentId,
        Long wordId,
        String content,
        int likeCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        WriterInfoDto writerInfo
) {

    public static LikedCommentDto of(Account account, Comment comment) {
        return new LikedCommentDto(
                comment.getId(),
                comment.getWordId(),
                comment.getContent(),
                comment.getLikeCount(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                new WriterInfoDto(account.getId(), account.getNickname(), account.getProfileImage())
        );
    }

    public record WriterInfoDto(Long id, String nickname, String profileImage) {
    }
}
