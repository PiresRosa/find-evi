package com.dnd.spaced.domain.comment.presentation.dto.response;

import com.dnd.spaced.domain.comment.application.dto.response.MultipleCommentInfoDto;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record MultipleCommentInfoResponse(
        List<CommentResponse> comments,
        Long lastCommentId,
        Integer lastLikeCount
) {


    public static MultipleCommentInfoResponse of(List<MultipleCommentInfoDto> dtos, String baseUrl, String imageUri) {
        List<CommentResponse> comments = dtos.stream()
                                             .map(dto -> CommentResponse.of(dto, baseUrl, imageUri))
                                             .toList();

        if (comments.isEmpty()) {
            return new MultipleCommentInfoResponse(Collections.emptyList(), null, null);
        }

        CommentResponse lastCommentResponse = comments.get(comments.size() - 1);

        return new MultipleCommentInfoResponse(comments, lastCommentResponse.commentId, lastCommentResponse.likeCount);
    }

    public record CommentResponse(
            Long commentId,
            Long wordId,
            String content,
            int likeCount,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            WriterInfoResponse writerInfo,
            boolean isLike
    ) {

        public static CommentResponse of(MultipleCommentInfoDto dto, String baseUrl, String imageUri) {
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
                    writerInfoResponse,
                    dto.isLike()
            );
        }
    }

    public record WriterInfoResponse(Long id, String nickname, String profileImage) {
    }
}
