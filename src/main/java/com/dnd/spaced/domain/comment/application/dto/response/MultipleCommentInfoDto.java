package com.dnd.spaced.domain.comment.application.dto.response;

import com.dnd.spaced.domain.account.domain.CareerInfo;
import com.dnd.spaced.domain.comment.domain.repository.dto.response.CommentInfoWithLikeDto;
import java.time.LocalDateTime;

public record MultipleCommentInfoDto(
        Long commentId,
        Long wordId,
        String content,
        int likeCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        WriterInfoDto writerInfo,
        boolean isLike
) {

    public static MultipleCommentInfoDto from(CommentInfoWithLikeDto dto) {
        return new MultipleCommentInfoDto(
                dto.commentId(),
                dto.wordId(),
                dto.content(),
                dto.likeCount(),
                dto.createdAt(),
                dto.updatedAt(),
                new WriterInfoDto(dto.writerId(), dto.writerNickname(), dto.writerProfileImage(), dto.careerInfo()),
                dto.likeAccountId() != null
        );
    }

    public record WriterInfoDto(Long id, String nickname, String profileImage, CareerInfo careerInfo) {
    }
}
