package com.dnd.spaced.domain.comment.presentation.dto.response;

import com.dnd.spaced.domain.comment.application.dto.response.MultipleCommentInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record MultipleCommentInfoResponse(
        @Schema(description = "댓글 정보")
        List<CommentResponse> comments,

        @Schema(description = "마지막으로 조회한 댓글 ID", nullable = true)
        Long lastCommentId,

        @Schema(description = "마지막으로 조회한 좋아요 개수", nullable = true)
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
            @Schema(description = "댓글 ID")
            Long commentId,

            @Schema(description = "용어 ID")
            Long wordId,

            @Schema(description = "댓글 내용")
            String content,

            @Schema(description = "댓글 좋아요 수")
            int likeCount,

            @Schema(description = "댓글 생성 일자")
            LocalDateTime createdAt,

            @Schema(description = "댓글 수정 일자")
            LocalDateTime updatedAt,

            @Schema(description = "댓글 작성자 정보")
            WriterInfoResponse writerInfo,

            @Schema(description = "좋아요 여부")
            boolean isLike
    ) {

        public static CommentResponse of(MultipleCommentInfoDto dto, String baseUrl, String imageUri) {
            WriterInfoResponse writerInfoResponse = new WriterInfoResponse(
                    dto.writerInfo().id(),
                    dto.writerInfo().nickname(),
                    baseUrl + imageUri + dto.writerInfo().profileImage(),
                    dto.writerInfo().careerInfo().getJobGroup().getName(),
                    dto.writerInfo().careerInfo().getCompany().getName(),
                    dto.writerInfo().careerInfo().getExperience().getName()
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

    public record WriterInfoResponse(
            @Schema(description = "회원 ID")
            Long id,

            @Schema(description = "회원 닉네임 ID")
            String nickname,

            @Schema(description = "회원 프로필 이미지")
            String profileImage,

            @Schema(description = "회원 직군")
            String jobGroup,

            @Schema(description = "회원 회사")
            String company,

            @Schema(description = "회원 연차")
            String experience
    ) {
    }
}
