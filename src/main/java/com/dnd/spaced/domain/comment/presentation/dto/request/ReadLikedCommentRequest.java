package com.dnd.spaced.domain.comment.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record ReadLikedCommentRequest(
        @Schema(description = "마지막으로 조회한 댓글 ID", requiredMode = RequiredMode.NOT_REQUIRED)
        Long lastCommentId
) {
}
