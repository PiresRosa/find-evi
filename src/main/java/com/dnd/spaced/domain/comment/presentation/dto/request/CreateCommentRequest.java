package com.dnd.spaced.domain.comment.presentation.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record CreateCommentRequest(@NotEmpty String content) {
}
