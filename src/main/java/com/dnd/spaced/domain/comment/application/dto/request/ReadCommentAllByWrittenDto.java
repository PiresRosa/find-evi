package com.dnd.spaced.domain.comment.application.dto.request;

import org.springframework.data.domain.Pageable;

public record ReadCommentAllByWrittenDto(String email, Long lastCommentId, Pageable pageable) {
}
