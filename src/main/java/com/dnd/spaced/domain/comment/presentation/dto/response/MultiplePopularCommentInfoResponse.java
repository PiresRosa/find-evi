package com.dnd.spaced.domain.comment.presentation.dto.response;

import com.dnd.spaced.domain.comment.application.dto.response.MultiplePopularCommentInfoDto;
import com.dnd.spaced.domain.comment.application.dto.response.MultiplePopularCommentInfoDto.PronunciationInfoDto;
import com.dnd.spaced.domain.comment.application.dto.response.MultiplePopularCommentInfoDto.WordInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

public record MultiplePopularCommentInfoResponse(@Schema(description = "댓글 정보") List<PopularCommentResponse> comments) {

    public static MultiplePopularCommentInfoResponse from(List<MultiplePopularCommentInfoDto> comments) {
        List<PopularCommentResponse> responses = comments.stream()
                                                         .map(PopularCommentResponse::from)
                                                         .toList();

        return new MultiplePopularCommentInfoResponse(responses);
    }

    public record PopularCommentResponse(
            @Schema(description = "댓글 정보")
            Long id,

            @Schema(description = "댓글 내용")
            String content,

            @Schema(description = "댓글 정보")
            int likeCount,

            @Schema(description = "댓글 생성 일자")
            LocalDateTime createdAt,

            @Schema(description = "댓글 수정 일자")
            LocalDateTime updatedAt,

            @Schema(description = "좋아요 여부")
            boolean isLike,

            @Schema(description = "용어 정보")
            WordInfoResponse wordInfo
    ) {

        public static PopularCommentResponse from(MultiplePopularCommentInfoDto dto) {
            WordInfoDto wordInfoDto = dto.wordInfo();
            WordInfoResponse wordInfo = new WordInfoResponse(
                    wordInfoDto.id(),
                    wordInfoDto.name(),
                    wordInfoDto.categoryName()
            );

            return new PopularCommentResponse(
                    dto.commentId(),
                    dto.content(),
                    dto.likeCount(),
                    dto.createdAt(),
                    dto.updatedAt(),
                    dto.isLike(),
                    wordInfo
            );
        }
    }

    public record WordInfoResponse(
            @Schema(description = "용어 ID")
            Long id,

            @Schema(description = "용어 이름")
            String name,

            @Schema(description = "용어 카테고리", allowableValues = {"개발", "디자인", "비즈니스"})
            String categoryName
    ) {
    }
}
