package com.dnd.spaced.domain.admin.presentation.dto;

import com.dnd.spaced.domain.admin.application.dto.response.ReportInfoDto;
import com.dnd.spaced.domain.admin.presentation.dto.response.ReportListResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminControllerMapper {
    public static ReportListResponse toReportListResponse(List<ReportInfoDto> reports, Long lastReportId) {
        Long newLastReportId = reports.isEmpty() ? lastReportId : reports.get(reports.size() - 1).id();
        return new ReportListResponse(reports, newLastReportId);
    }
}
