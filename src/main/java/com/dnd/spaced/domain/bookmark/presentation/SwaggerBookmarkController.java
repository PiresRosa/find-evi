package com.dnd.spaced.domain.bookmark.presentation;

import com.dnd.spaced.domain.bookmark.presentation.dto.request.BookmarkConditionRequest;
import com.dnd.spaced.domain.bookmark.presentation.dto.response.BookmarkWordResponse;
import com.dnd.spaced.global.docs.annotation.ExceptionSpec;
import com.dnd.spaced.global.exception.ExceptionCode;
import com.dnd.spaced.global.resolver.auth.AuthAccount;
import com.dnd.spaced.global.resolver.auth.AuthAccountInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "북마크 관련 API", description = "북마크 등록/삭제, 북마크한 용어 목록 조회")
public interface SwaggerBookmarkController {

    @Operation(summary = "북마크 등록/삭제", description = "북마크 등록/삭제 API")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ExceptionSpec(values = {ExceptionCode.WORD_NOT_FOUND,})
    ResponseEntity<Void> processBookmark(
            @Parameter(hidden = true) @AuthAccount AuthAccountInfo accountInfo,
            @Parameter(description = "북마크 등록/삭제하고자 하는 용어 ID") @PathVariable Long id
    );

    @Operation(summary = "북마크한 용어 목록 조회", description = "북마크한 용어 목록 조회 API")
    @Parameters({
            @Parameter(name = "lastBookmarkId", description = "마지막으로 조회한 북마크 ID", schema = @Schema(requiredMode = RequiredMode.NOT_REQUIRED)),
            @Parameter(name = "size", description = "한 페이지에 조회 가능한 용어 개수", schema = @Schema(defaultValue = "15", requiredMode = RequiredMode.NOT_REQUIRED)),
            @Parameter(name = "sortBy", description = "정렬 기준(등록순 고정)", schema = @Schema(defaultValue = "id", allowableValues = "id", requiredMode = RequiredMode.NOT_REQUIRED))
    })
    @ApiResponse(responseCode = "200", description = "OK")
    @ExceptionSpec(values = {ExceptionCode.FORBIDDEN_BOOKMARK})
    ResponseEntity<BookmarkWordResponse> findAllBy(
            @Parameter(hidden = true) @AuthAccount AuthAccountInfo accountInfo,
            @Parameter(hidden = true) BookmarkConditionRequest request,
            @Parameter(hidden = true) Pageable pageable
    );
}
