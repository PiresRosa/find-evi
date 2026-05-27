package com.dnd.spaced.domain.comment.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record UpdateCommentRequest(
        @NotEmpty
        @Schema(description = "수정할 댓글 내용", minLength = 1, maxLength = 100)
        String content
) {
}
