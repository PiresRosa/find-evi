package com.dnd.spaced.domain.comment.application.dto.request;

import org.springframework.data.domain.Pageable;

public record CommentConditionInfoDto(Long lastCommentId, int lastLikeCount, Pageable pageable, String email) {
}
