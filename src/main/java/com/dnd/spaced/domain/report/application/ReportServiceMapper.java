package com.dnd.spaced.domain.report.application;

import com.dnd.spaced.domain.report.application.dto.request.ReportInfoDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReportServiceMapper {

    public static ReportInfoDto of(String email, String reasonName, Long commentId) {
        return new ReportInfoDto(email, reasonName, commentId);
    }
}
