package com.dnd.spaced.domain.comment.presentation.dto.response;

import com.dnd.spaced.domain.comment.application.dto.response.MultiplePopularCommentInfoDto;
import com.dnd.spaced.domain.comment.application.dto.response.MultiplePopularCommentInfoDto.PronunciationInfoDto;
import com.dnd.spaced.domain.comment.application.dto.response.MultiplePopularCommentInfoDto.WordInfoDto;
import com.dnd.spaced.domain.comment.application.dto.response.MultiplePopularCommentInfoDto.WriterInfoDto;
import java.time.LocalDateTime;
import java.util.List;

public record MultiplePopularCommentInfoResponse(List<CommentResponse> comments) {

    public static MultiplePopularCommentInfoResponse of(
            List<MultiplePopularCommentInfoDto> dtos,
            String baseUrl,
            String imageUri
    ) {
        List<CommentResponse> responses = dtos.stream()
                                              .map(dto -> CommentResponse.of(dto, baseUrl, imageUri))
                                              .toList();

        return new MultiplePopularCommentInfoResponse(responses);
    }

    public record CommentResponse(
            Long id,
            String content,
            int likeCount,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            boolean isLike,
            WordInfoResponse wordInfo,
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
            PronunciationInfoResponse pronunciationInfo = new PronunciationInfoResponse(
                    pronunciationInfoDto.korean(),
                    pronunciationInfoDto.english()
            );
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
            Long id,
            String name,
            String categoryName,
            PronunciationInfoResponse pronunciationInfo
    ) {
    }

    public record PronunciationInfoResponse(String korean, String english) {
    }

    public record WriterInfoResponse(Long id, String nickname, String profileImage) {
    }
}
