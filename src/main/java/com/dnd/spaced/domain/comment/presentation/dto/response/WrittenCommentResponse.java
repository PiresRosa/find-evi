package com.dnd.spaced.domain.comment.presentation.dto.response;

import com.dnd.spaced.domain.comment.application.dto.response.WrittenCommentDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record WrittenCommentResponse(
        @Schema(description = "댓글 정보")
        List<CommentResponse> comments,

        @Schema(description = "마지막으로 조회한 댓글 ID", nullable = true)
        Long lastCommentId
) {

    public static WrittenCommentResponse of(List<WrittenCommentDto> dtos, String baseUrl, String imageUri) {
        if (dtos.isEmpty()) {
            return new WrittenCommentResponse(Collections.emptyList(), null);
        }

        List<CommentResponse> comments = dtos.stream()
                                             .map(dto -> CommentResponse.of(dto, baseUrl, imageUri))
                                             .toList();

        return new WrittenCommentResponse(comments, comments.get(comments.size() - 1).commentId());
    }

    public record CommentResponse(
            @Schema(description = "댓글 ID")
            Long commentId,

            @Schema(description = "용어 ID")
            Long wordId,

            @Schema(description = "댓글 내용")
            String content,

            @Schema(description = "댓글 ID")
            int likeCount,

            @Schema(description = "댓글 생성 일자")
            LocalDateTime createdAt,

            @Schema(description = "댓글 수정 일자")
            LocalDateTime updatedAt,

            @Schema(description = "댓글 작성자 정보")
            WriterInfoResponse writerInfo
    ) {

        public static CommentResponse of(WrittenCommentDto dto, String baseUrl, String imageUri) {
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
            @Schema(description = "회원 ID")
            Long id,

            @Schema(description = "회원 닉네임 ID")
            String nickname,

            @Schema(description = "회원 프로필 이미지")
            String profileImage
    ) {
    }
}
