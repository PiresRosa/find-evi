package com.dnd.spaced.domain.comment.application.dto.response;

import com.dnd.spaced.domain.comment.domain.repository.dto.response.PopularCommentInfoDto;
import java.time.LocalDateTime;

public record MultiplePopularCommentInfoDto(
        Long commentId,
        WordInfoDto wordInfo,
        String content,
        int likeCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        WriterInfoDto writerInfo,
        boolean isLike
) {

    public static MultiplePopularCommentInfoDto from(PopularCommentInfoDto dto) {
        PronunciationInfoDto pronunciationInfo = new PronunciationInfoDto(
                dto.pronunciation().getKorean(),
                dto.pronunciation().getEnglish()
        );

        return new MultiplePopularCommentInfoDto(
                dto.commentId(),
                new WordInfoDto(dto.wordId(), dto.wordName(), dto.wordCategory().getName(), pronunciationInfo),
                dto.content(),
                dto.likeCount(),
                dto.createdAt(),
                dto.updatedAt(),
                new WriterInfoDto(dto.writerId(), dto.writerNickname(), dto.writerProfileImage()),
                dto.likeAccountId() != null
        );
    }

    public record WriterInfoDto(Long id, String nickname, String profileImage) {
    }

    public record WordInfoDto(Long id, String name, String categoryName, PronunciationInfoDto pronunciationInfo) {
    }

    public record PronunciationInfoDto(String korean, String english) {
    }
}
