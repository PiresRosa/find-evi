package com.dnd.spaced.domain.report.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotEmpty;

public record ReportInfoRequest(

        @Schema(description = "신고 사유", requiredMode = RequiredMode.REQUIRED, allowableValues = {
                "광고 및 홍보성 내용",
                "개인정보 노출 위험",
                "댓글 도배",
                "욕설, 음란 등 부적절한 내용",
                "기타"
        })
        @NotEmpty String reasonName
) {
}
