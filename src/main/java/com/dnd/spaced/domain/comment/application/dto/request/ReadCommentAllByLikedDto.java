package com.dnd.spaced.domain.comment.application.dto.request;

import org.springframework.data.domain.Pageable;

public record ReadCommentAllByLikedDto(String email, Long lastCommentId, Pageable pageable) {
}
