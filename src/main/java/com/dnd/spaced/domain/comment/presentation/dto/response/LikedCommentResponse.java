package com.dnd.spaced.domain.comment.presentation.dto.response;

import com.dnd.spaced.domain.comment.application.dto.response.LikedCommentDto;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record LikedCommentResponse(List<CommentResponse> comments, Long lastCommentId) {

    public static LikedCommentResponse of(List<LikedCommentDto> dtos, String baseUrl, String imageUri) {
        if (dtos.isEmpty()) {
            return new LikedCommentResponse(Collections.emptyList(), null);
        }

        List<CommentResponse> comments = dtos.stream()
                                             .map(dto -> CommentResponse.of(dto, baseUrl, imageUri))
                                             .toList();

        return new LikedCommentResponse(comments, comments.get(comments.size() - 1).commentId());
    }

    public record CommentResponse(
            Long commentId,
            Long wordId,
            String content,
            int likeCount,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            WriterInfoResponse writerInfo
    ) {

        public static CommentResponse of(LikedCommentDto dto, String baseUrl, String imageUri) {
            WriterInfoResponse writerInfoResponse = new WriterInfoResponse(
                    dto.writerInfo().id(),
                    dto.writerInfo().nickname(),
                    baseUrl + imageUri + dto.writerInfo().profileImage()
            );

            return new CommentResponse(
                    dto.commentId(),
                    dto.wordId(),
                    dto.content(),
                    dto.likeCount(),
                    dto.createdAt(),
                    dto.updatedAt(),
                    writerInfoResponse
            );
        }
    }

    public record WriterInfoResponse(
            Long id,
            String nickname,
            String profileImage
    ) {
    }
}
