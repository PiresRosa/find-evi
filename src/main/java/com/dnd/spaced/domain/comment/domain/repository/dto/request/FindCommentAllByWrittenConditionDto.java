package com.dnd.spaced.domain.comment.domain.repository.dto.request;

import org.springframework.data.domain.Pageable;

public record FindCommentAllByWrittenConditionDto(Long accountId, Long lastCommentId, Pageable pageable) {
}
