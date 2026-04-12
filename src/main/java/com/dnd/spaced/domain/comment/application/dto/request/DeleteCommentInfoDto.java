package com.dnd.spaced.domain.comment.application.dto.request;

public record DeleteCommentInfoDto(String email, Long wordId, Long commentId) {
}
