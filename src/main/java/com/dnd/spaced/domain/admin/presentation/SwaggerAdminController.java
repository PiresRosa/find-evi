package com.dnd.spaced.domain.admin.presentation;

import com.dnd.spaced.domain.admin.application.dto.request.AdminWordRequestDto;
import com.dnd.spaced.domain.admin.presentation.dto.request.AdminMultipleWordConditionRequest;
import com.dnd.spaced.domain.admin.presentation.dto.response.AdminWordResponse;
import com.dnd.spaced.domain.admin.presentation.dto.response.ReportListResponse;
import com.dnd.spaced.global.docs.annotation.ExceptionSpec;
import com.dnd.spaced.global.docs.annotation.ExcludeCommonHeaderSpec;
import com.dnd.spaced.global.docs.annotation.NotRequiredCommonHeaderSpec;
import com.dnd.spaced.global.exception.ExceptionCode;
import com.dnd.spaced.global.resolver.word.WordSortCondition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "관리자 관련 API", description = "용어 생성, 용어 삭제, 용어 수정, 신고 수락, 신고 무시, 신고 목록 조회")
public interface SwaggerAdminController {

    @ExcludeCommonHeaderSpec
    @Operation(summary = "용어 목록 조회", description = "관리자가 용어 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "OK")
    ResponseEntity<List<AdminWordResponse>> findAllBy(
            AdminMultipleWordConditionRequest request,
            @WordSortCondition Pageable pageable
    );

    @ExcludeCommonHeaderSpec
    @Operation(summary = "용어 등록", description = "관리자가 새로운 용어를 등록합니다.")
    @ApiResponse(responseCode = "201", description = "Created")
    ResponseEntity<Void> createWord(
            @Parameter(hidden = true) Authentication authentication,
            @RequestBody @Valid AdminWordRequestDto wordRequestDto
    );

    @ExcludeCommonHeaderSpec
    @Operation(summary = "용어 삭제", description = "관리자가 용어를 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ExceptionSpec(values = {
            ExceptionCode.WORD_NOT_FOUND
    })
    ResponseEntity<Void> deleteWord(
            @Parameter(hidden = true) Authentication authentication,
            @Parameter(description = "용어 ID") @PathVariable Long id
    );

    @ExcludeCommonHeaderSpec
    @Operation(summary = "용어 수정", description = "관리자가 용어를 수정합니다.")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ExceptionSpec(values = {
            ExceptionCode.WORD_NOT_FOUND
    })
    ResponseEntity<Void> updateWord(
            @Parameter(hidden = true) Authentication authentication,
            @Parameter(description = "용어 ID") @PathVariable Long id,
            @RequestBody @Valid AdminWordRequestDto wordRequestDto
    );

    @ExcludeCommonHeaderSpec
    @Operation(summary = "용어 조회", description = "관리자가 용어를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = AdminWordResponse.class)))
    @ExceptionSpec(values = {
            ExceptionCode.WORD_NOT_FOUND
    })
    ResponseEntity<AdminWordResponse> getWord(
            @Parameter(description = "용어 ID") @PathVariable Long id
    );

    @ExcludeCommonHeaderSpec
    @Operation(summary = "신고 수락", description = "관리자가 신고를 수락합니다.")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ExceptionSpec(values = {
            ExceptionCode.REPORTED_COMMENT_NOT_FOUND,
            ExceptionCode.COMMENT_NOT_FOUND
    })
    ResponseEntity<Void> acceptReport(
            @Parameter(description = "신고 ID") @PathVariable Long id
    );

    @ExcludeCommonHeaderSpec
    @Operation(summary = "신고 무시", description = "관리자가 신고를 무시합니다.")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ExceptionSpec(values = {ExceptionCode.REPORT_NOT_FOUND})
    ResponseEntity<Void> ignoreReport(
            @Parameter(description = "신고 ID") @PathVariable Long id
    );

    @NotRequiredCommonHeaderSpec
    @Operation(summary = "신고 목록 조회", description = "관리자가 신고 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ReportListResponse.class)))
    ResponseEntity<ReportListResponse> findReports(
            @Parameter(description = "마지막으로 조회한 신고 ID", required = false) @RequestParam(required = false) Long lastReportId
    );
}
