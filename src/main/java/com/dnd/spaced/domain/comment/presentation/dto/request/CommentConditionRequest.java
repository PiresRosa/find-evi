package com.dnd.spaced.domain.comment.presentation.dto.request;

public record CommentConditionRequest(Long lastCommentId, Integer lastLikeCount) {
}
