package com.dnd.spaced.domain.comment.domain.repository.dto.response;

import com.dnd.spaced.domain.word.domain.Category;
import com.dnd.spaced.domain.word.domain.Pronunciation;
import java.time.LocalDateTime;

public record PopularCommentInfoDto(
        Long commentId,
        Long writerId,
        String writerNickname,
        String writerProfileImage,
        Long wordId,
        String wordName,
        Category wordCategory,
        Pronunciation pronunciation,
        String content,
        int likeCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long likeAccountId
) {
}
