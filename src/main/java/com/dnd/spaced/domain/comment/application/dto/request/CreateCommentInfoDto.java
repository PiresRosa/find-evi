package com.dnd.spaced.domain.comment.application.dto.request;

public record CreateCommentInfoDto(String email, Long wordId, String content) {
}
