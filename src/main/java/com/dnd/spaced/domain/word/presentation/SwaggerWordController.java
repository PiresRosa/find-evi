package com.dnd.spaced.domain.word.presentation;

import com.dnd.spaced.domain.word.presentation.dto.request.MultipleWordConditionRequest;
import com.dnd.spaced.domain.word.presentation.dto.request.WordSearchRequest;
import com.dnd.spaced.domain.word.presentation.dto.response.*;
import com.dnd.spaced.global.docs.annotation.ExceptionSpec;
import com.dnd.spaced.global.docs.annotation.ExcludeCommonHeaderSpec;
import com.dnd.spaced.global.docs.annotation.NotRequiredCommonHeaderSpec;
import com.dnd.spaced.global.exception.ExceptionCode;
import com.dnd.spaced.global.resolver.auth.AuthAccount;
import com.dnd.spaced.global.resolver.auth.AuthAccountInfo;
import com.dnd.spaced.global.resolver.word.PopularWordSortCondition;
import com.dnd.spaced.global.resolver.word.SearchWordSortCondition;
import com.dnd.spaced.global.resolver.word.WordSortCondition;
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

@Tag(name = "용어 관련 API", description = "용어 사전 목록 조회, 용어 상세 내용 조회, 최근 추가된 용어 목록 조회, 용어 검색어에 따른 자동완성, 용어 검색, 많이 찾아본 용어 목록 조회")
public interface SwaggerWordController {

    @ExcludeCommonHeaderSpec
    @Operation(summary = "용어 사전 목록 조회", description = "용어 사전 목록 조회 API")
    @Parameters({
            @Parameter(name = "category", description = "용어 카테고리", schema = @Schema(defaultValue = "전체 실무", allowableValues = {"전체 실무", "비즈니스", "개발", "디자인"}, requiredMode = RequiredMode.NOT_REQUIRED)),
            @Parameter(name = "lastWordName", description = "마지막으로 조회한 용어 이름", schema = @Schema(requiredMode = RequiredMode.NOT_REQUIRED)),
            @Parameter(name = "size", description = "한 페이지에 조회 가능한 용어 개수", schema = @Schema(defaultValue = "15", requiredMode = RequiredMode.NOT_REQUIRED)),
            @Parameter(name = "sortBy", description = "정렬 기준(사전순 고정)", schema = @Schema(defaultValue = "name", allowableValues = "name", requiredMode = RequiredMode.NOT_REQUIRED))
    })
    @ApiResponse(responseCode = "200", description = "OK")
    ResponseEntity<ListWordInfoResponse> findAllBy(
            @Parameter(hidden = true) MultipleWordConditionRequest request,
            @Parameter(hidden = true) @WordSortCondition Pageable pageable
    );

    @NotRequiredCommonHeaderSpec
    @Operation(summary = "용어 상세 내용 조회", description = "용어 상세 내용 조회 API")
    @ApiResponse(responseCode = "200", description = "OK")
    @ExceptionSpec(values = {ExceptionCode.WORD_NOT_FOUND})
    ResponseEntity<DetailWordInfoResponse> findBy(
            @Parameter(hidden = true) @AuthAccount(required = false) AuthAccountInfo accountInfo,
            @Schema(description = "조회할 용어 ID") @PathVariable Long id
    );

    @ExcludeCommonHeaderSpec
    @Operation(summary = "용어 검색어에 따른 자동완성", description = "용어 검색어에 따른 자동완성 API")
    @ApiResponse(responseCode = "200", description = "OK")
    ResponseEntity<InputWordCandidateResponse> findCandidateAllBy(
            @Schema(description = "검색할 용어 이름") String name
    );

    @ExcludeCommonHeaderSpec
    @Operation(summary = "최근 등록된 용어 조회", description = "최근 등록된 용어 조회 API")
    @ApiResponse(responseCode = "200", description = "OK")
    ResponseEntity<SimpleListWordInfoResponse> findRecentWords();

    @ExcludeCommonHeaderSpec
    @Operation(summary = "오늘의 용어 조회", description = "오늘의 용어 조회 API")
    @ApiResponse(responseCode = "200", description = "OK")
    ResponseEntity<SimpleListWordInfoResponse> findRandomWords();

    @ExcludeCommonHeaderSpec
    @Operation(summary = "용어 검색", description = "용어 검색 API")
    @Parameters({
            @Parameter(name = "name", description = "검색할 용어 이름", schema = @Schema(requiredMode = RequiredMode.NOT_REQUIRED)),
            @Parameter(name = "pronunciation", description = "검색할 용어 발음", schema = @Schema(requiredMode = RequiredMode.NOT_REQUIRED)),
            @Parameter(name = "category", description = "검색할 용어 카테고리", schema = @Schema(defaultValue = "전체 실무", allowableValues = {"전체 실무", "비즈니스", "개발", "디자인"}, requiredMode = RequiredMode.NOT_REQUIRED)),
            @Parameter(name = "lastWordName", description = "마지막으로 조회한 용어 이름", schema = @Schema(requiredMode = RequiredMode.NOT_REQUIRED)),
            @Parameter(name = "size", description = "한 페이지에 조회 가능한 용어 개수", schema = @Schema(defaultValue = "15", requiredMode = RequiredMode.NOT_REQUIRED)),
            @Parameter(name = "sortBy", description = "정렬 기준(사전순 고정)", schema = @Schema(defaultValue = "name", allowableValues = "name", requiredMode = RequiredMode.NOT_REQUIRED))
    })
    @ApiResponse(responseCode = "200", description = "OK")
    ResponseEntity<MultipleSearchWordInfoResponse> search(
            @Parameter(hidden = true) WordSearchRequest request,
            @Parameter(hidden = true) @SearchWordSortCondition Pageable pageable
    );

    @ExcludeCommonHeaderSpec
    @Operation(summary = "많이 찾아본 용어 목록 조회", description = "많이 찾아본 용어 목록 조회 API")
    @Parameters({
            @Parameter(name = "size", description = "한 페이지에 조회 가능한 용어 개수", schema = @Schema(defaultValue = "10", requiredMode = RequiredMode.NOT_REQUIRED)),
            @Parameter(name = "sortBy", description = "정렬 기준(조회 수 고정)", schema = @Schema(defaultValue = "viewCount", allowableValues = "viewCount", requiredMode = RequiredMode.NOT_REQUIRED))
    })
    @ApiResponse(responseCode = "200", description = "OK")
    ResponseEntity<PopularWordResponse> findAllByPopular(
            @Parameter(hidden = true) @PopularWordSortCondition Pageable pageable
    );
}
