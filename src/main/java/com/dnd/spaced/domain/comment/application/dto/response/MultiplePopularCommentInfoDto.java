package com.dnd.spaced.domain.comment.application.dto.response;

import com.dnd.spaced.domain.comment.domain.repository.dto.response.PopularCommentInfoDto;
import com.dnd.spaced.domain.comment.domain.repository.dto.response.PopularCommentWithoutIsLikeDto;
import java.time.LocalDateTime;

public record MultiplePopularCommentInfoDto(
        Long commentId,
        WordInfoDto wordInfo,
        String content,
        int likeCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean isLike
) {

    public static MultiplePopularCommentInfoDto from(PopularCommentInfoDto dto) {
        PronunciationInfoDto pronunciationInfo = new PronunciationInfoDto(dto.pronunciation().getEnglish());

        return new MultiplePopularCommentInfoDto(
                dto.commentId(),
                new WordInfoDto(dto.wordId(), dto.wordName(), dto.wordCategory().getName(), pronunciationInfo),
                dto.content(),
                dto.likeCount(),
                dto.createdAt(),
                dto.updatedAt(),
                dto.likeAccountId() != null
        );
    }

    public static MultiplePopularCommentInfoDto from(PopularCommentWithoutIsLikeDto dto) {
        PronunciationInfoDto pronunciationInfo = new PronunciationInfoDto(dto.pronunciation().getEnglish());

        return new MultiplePopularCommentInfoDto(
                dto.commentId(),
                new WordInfoDto(dto.wordId(), dto.wordName(), dto.wordCategory().getName(), pronunciationInfo),
                dto.content(),
                dto.likeCount(),
                dto.createdAt(),
                dto.updatedAt(),
                false
        );
    }


    public record WordInfoDto(Long id, String name, String categoryName, PronunciationInfoDto pronunciationInfo) {
    }

    public record PronunciationInfoDto(String english) {
    }
}
