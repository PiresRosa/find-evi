package com.dnd.spaced.domain.admin.presentation.dto.response;


import com.dnd.spaced.domain.admin.application.dto.response.ReportInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ReportListResponse(

        @Schema(description = "신고 정보")
        List<ReportInfoDto> reports,

        @Schema(description = "마지막으로 조회한 신고 댓글 ID")
        Long lastReportId
) {
}
