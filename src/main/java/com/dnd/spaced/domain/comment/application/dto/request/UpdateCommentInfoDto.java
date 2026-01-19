package com.dnd.spaced.domain.comment.application.dto.request;

public record UpdateCommentInfoDto(String email, Long commentId, String content) {
}
