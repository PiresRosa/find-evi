package com.dnd.spaced.domain.admin.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ReportInfoDto(

        @Schema(description = "신고 ID")
        Long id,

        @Schema(description = "신고 사유")
        String reason) {
}
