package com.dnd.spaced.domain.report.presentation;

import com.dnd.spaced.domain.report.presentation.dto.request.ReportInfoRequest;
import com.dnd.spaced.global.docs.annotation.ExceptionSpec;
import com.dnd.spaced.global.exception.ExceptionCode;
import com.dnd.spaced.global.resolver.auth.AuthAccount;
import com.dnd.spaced.global.resolver.auth.AuthAccountInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "신고 관련 API", description = "댓글 신고")
public interface SwaggerReportController {

    @Operation(summary = "댓글 신고", description = "댓글 신고 API")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ExceptionSpec(values = {
            ExceptionCode.FORBIDDEN_REPORT_ACCOUNT,
            ExceptionCode.REPORTED_COMMENT_NOT_FOUND,
            ExceptionCode.INVALID_REASON_NAME
    })
    ResponseEntity<Void> save(
            @Parameter(hidden = true) @AuthAccount AuthAccountInfo accountInfo,
            @Valid @RequestBody ReportInfoRequest request,
            @Parameter(description = "신고할 댓글 ID") @PathVariable Long id
    );
}
