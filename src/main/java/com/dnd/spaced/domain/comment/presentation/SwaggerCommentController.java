package com.dnd.spaced.domain.comment.presentation;

import com.dnd.spaced.domain.comment.presentation.dto.request.CommentConditionRequest;
import com.dnd.spaced.domain.comment.presentation.dto.request.CreateCommentRequest;
import com.dnd.spaced.domain.comment.presentation.dto.request.UpdateCommentRequest;
import com.dnd.spaced.domain.comment.presentation.dto.response.MultipleCommentInfoResponse;
import com.dnd.spaced.domain.comment.presentation.dto.response.MultiplePopularCommentInfoResponse;
import com.dnd.spaced.global.docs.annotation.ExceptionSpec;
import com.dnd.spaced.global.docs.annotation.NotRequiredCommonHeaderSpec;
import com.dnd.spaced.global.exception.ExceptionCode;
import com.dnd.spaced.global.resolver.auth.AuthAccount;
import com.dnd.spaced.global.resolver.auth.AuthAccountInfo;
import com.dnd.spaced.global.resolver.comment.CommentSortCondition;
import com.dnd.spaced.global.resolver.comment.PopularCommentSortCondition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "댓글 관련 API", description = "댓글 등록, 댓글 수정, 댓글 삭제, 댓글 좋아요, 댓글 목록 조회, 지금 반응이 뜨거운 댓글 목록 조회")
public interface SwaggerCommentController {

    @Operation(summary = "댓글 등록", description = "댓글 등록 API")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ExceptionSpec(values = {
            ExceptionCode.INVALID_PATH_VARIABLE,
            ExceptionCode.VALIDATION_ERROR,
            ExceptionCode.UNAUTHORIZED_COMMENT,
            ExceptionCode.COMMENT_WORD_NOT_FOUND,
            ExceptionCode.INVALID_CONTENT
    })
    ResponseEntity<Void> save(
            @Parameter(hidden = true) @AuthAccount AuthAccountInfo accountInfo,
            @Parameter(description = "용어 ID") @PathVariable Long wordId,
            @RequestBody @Valid CreateCommentRequest request
    );

    @Operation(summary = "댓글 수정", description = "댓글 수정 API")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ExceptionSpec(values = {
            ExceptionCode.INVALID_PATH_VARIABLE,
            ExceptionCode.VALIDATION_ERROR,
            ExceptionCode.UNAUTHORIZED_COMMENT,
            ExceptionCode.COMMENT_NOT_FOUND,
            ExceptionCode.FORBIDDEN_UPDATE_COMMENT,
            ExceptionCode.INVALID_CONTENT
    })
    ResponseEntity<Void> update(
            @Parameter(hidden = true) @AuthAccount AuthAccountInfo accountInfo,
            @Parameter(description = "댓글 ID") @PathVariable Long commentId,
            @RequestBody @Valid UpdateCommentRequest request
    );

    @Operation(summary = "댓글 삭제", description = "댓글 삭제 API")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ExceptionSpec(values = {
            ExceptionCode.INVALID_PATH_VARIABLE,
            ExceptionCode.COMMENT_NOT_FOUND,
            ExceptionCode.UNAUTHORIZED_COMMENT,
            ExceptionCode.FORBIDDEN_DELETE_COMMENT,
            ExceptionCode.COMMENT_WORD_NOT_FOUND
    })
    ResponseEntity<Void> delete(
            @Parameter(hidden = true) @AuthAccount AuthAccountInfo accountInfo,
            @Parameter(description = "용어 ID") @PathVariable Long wordId,
            @Parameter(description = "댓글 ID") @PathVariable Long commentId
    );

    @Operation(summary = "댓글 좋아요", description = "댓글 좋아요 API")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ExceptionSpec(values = {
            ExceptionCode.INVALID_PATH_VARIABLE,
            ExceptionCode.UNAUTHORIZED_COMMENT,
            ExceptionCode.COMMENT_NOT_FOUND
    })
    ResponseEntity<Void> processLike(
            @Parameter(hidden = true) @AuthAccount AuthAccountInfo accountInfo,
            @Parameter(description = "댓글 ID") @PathVariable Long commentId
    );

    @NotRequiredCommonHeaderSpec
    @Operation(summary = "댓글 목록 조회", description = "댓글 목록 조회 API")
    @ApiResponse(responseCode = "200", description = "OK")
    @ExceptionSpec(values = {
            ExceptionCode.INVALID_PATH_VARIABLE,
            ExceptionCode.COMMENT_WORD_NOT_FOUND,
            ExceptionCode.UNSUPPORTED_COMMENT_SORT_CONDITION
    })
    @Parameters({
            @Parameter(name = "lastCommentId", description = "마지막으로 조회한 댓글 ID"),
            @Parameter(name = "lastLikeCount", description = "마지막으로 조회한 댓글 좋아요 개수"),
            @Parameter(name = "sortBy", description = "정렬 기준", schema = @Schema(defaultValue = "likeCount", allowableValues = {
                    "likeCount", "createdAt"})),
            @Parameter(name = "sortOrder", description = "정렬 방식", schema = @Schema(defaultValue = "desc", allowableValues = {
                    "asc", "desc"})),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MultipleCommentInfoResponse> findAllBy(
            @Parameter(hidden = true) @AuthAccount(required = false) AuthAccountInfo accountInfo,
            @Parameter(description = "용어 ID") @PathVariable Long wordId,
            @Parameter(hidden = true) CommentConditionRequest request,
            @Parameter(hidden = true) @CommentSortCondition Pageable pageable
    );

    @NotRequiredCommonHeaderSpec
    @Operation(summary = "지금 반응이 뜨거운 댓글 목록 조회", description = "지금 반응이 뜨거운 댓글 목록 조회 API")
    @ApiResponse(responseCode = "200", description = "OK")
    ResponseEntity<MultiplePopularCommentInfoResponse> findPopularAllBy(
            @Parameter(hidden = true) @AuthAccount(required = false) AuthAccountInfo accountInfo,
            @Parameter(hidden = true) @PopularCommentSortCondition Pageable pageable
    );
}
