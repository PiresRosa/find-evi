package com.dnd.spaced.domain.admin.presentation.dto.response;


import com.dnd.spaced.domain.admin.application.dto.response.ReportInfoDto;

import java.util.List;

public record ReportListResponse(List<ReportInfoDto> reports, Long lastReportId) {
}
