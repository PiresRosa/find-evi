package com.dnd.spaced.domain.comment.presentation.dto.response;

import com.dnd.spaced.domain.comment.application.dto.response.MultiplePopularCommentInfoDto;
import com.dnd.spaced.domain.comment.application.dto.response.MultiplePopularCommentInfoDto.PronunciationInfoDto;
import com.dnd.spaced.domain.comment.application.dto.response.MultiplePopularCommentInfoDto.WordInfoDto;
import com.dnd.spaced.domain.comment.application.dto.response.MultiplePopularCommentInfoDto.WriterInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

public record MultiplePopularCommentInfoResponse(@Schema(description = "댓글 정보") List<CommentResponse> comments) {

    public static MultiplePopularCommentInfoResponse of(
            List<MultiplePopularCommentInfoDto> comments,
            String baseUrl,
            String imageUri
    ) {
        List<CommentResponse> responses = comments.stream()
                                              .map(dto -> CommentResponse.of(dto, baseUrl, imageUri))
                                              .toList();

        return new MultiplePopularCommentInfoResponse(responses);
    }

    public record CommentResponse(
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
            WordInfoResponse wordInfo,

            @Schema(description = "댓글 작성 정보")
            WriterInfoResponse writerInfo
    ) {

        public static CommentResponse of(MultiplePopularCommentInfoDto dto, String baseUrl, String imageUri) {
            WriterInfoDto writerInfoDto = dto.writerInfo();
            WriterInfoResponse writerInfo = new WriterInfoResponse(
                    writerInfoDto.id(),
                    writerInfoDto.nickname(),
                    baseUrl + imageUri + writerInfoDto.profileImage()
            );
            WordInfoDto wordInfoDto = dto.wordInfo();
            PronunciationInfoDto pronunciationInfoDto = wordInfoDto.pronunciationInfo();
            PronunciationInfoResponse pronunciationInfo = new PronunciationInfoResponse(pronunciationInfoDto.english());
            WordInfoResponse wordInfo = new WordInfoResponse(
                    wordInfoDto.id(),
                    wordInfoDto.name(),
                    wordInfoDto.categoryName(),
                    pronunciationInfo
            );

            return new CommentResponse(
                    dto.commentId(),
                    dto.content(),
                    dto.likeCount(),
                    dto.createdAt(),
                    dto.updatedAt(),
                    dto.isLike(),
                    wordInfo,
                    writerInfo
            );
        }
    }

    public record WordInfoResponse(
            @Schema(description = "용어 ID")
            Long id,

            @Schema(description = "용어 이름")
            String name,

            @Schema(description = "용어 카테고리", allowableValues = {"개발", "디자인", "비즈니스"})
            String categoryName,

            @Schema(description = "용어 발음 정보")
            PronunciationInfoResponse pronunciationInfo
    ) {
    }

    public record PronunciationInfoResponse(
            @Schema(description = "용어 발음 기호")
            String english
    ) {
    }

    public record WriterInfoResponse(
            @Schema(description = "작성자 ID")
            Long id,

            @Schema(description = "작성자 닉네임")
            String nickname,

            @Schema(description = "작성자 프로필 이미지")
            String profileImage
    ) {
    }
}
