package com.dnd.spaced.domain.auth.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CareerInfoRequest(
        @Schema(description = "직군", allowableValues = {"개발자", "디자이너"})
        @NotBlank String jobGroup,

        @Schema(description = "회사", allowableValues = {"비공개", "대기업", "중견기업", "중소기업", "스타트업", "외국계", "취준생/인턴"})
        @NotBlank String company,

        @Schema(description = "연차", allowableValues = {"비공개", "1년 차 미만", "1~2년 차", "2~3년 차", "3~4년 차", "4~5년 차", "5년 차 이상"})
        @NotBlank String experience
) {
}
