package com.dnd.spaced.domain.report.presentation.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record ReportInfoRequest(@NotEmpty String reasonName) {
}
