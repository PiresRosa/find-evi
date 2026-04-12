package com.dnd.spaced.domain.comment.presentation;

import com.dnd.spaced.domain.comment.application.CommentService;
import com.dnd.spaced.domain.comment.application.dto.CommentServiceMapper;
import com.dnd.spaced.domain.comment.application.dto.request.CommentConditionInfoDto;
import com.dnd.spaced.domain.comment.application.dto.response.MultipleCommentInfoDto;
import com.dnd.spaced.domain.comment.application.dto.response.MultiplePopularCommentInfoDto;
import com.dnd.spaced.domain.comment.presentation.dto.CommentControllerMapper;
import com.dnd.spaced.domain.comment.presentation.dto.request.CommentConditionRequest;
import com.dnd.spaced.domain.comment.presentation.dto.request.CreateCommentRequest;
import com.dnd.spaced.domain.comment.presentation.dto.request.UpdateCommentRequest;
import com.dnd.spaced.domain.comment.presentation.dto.response.MultipleCommentInfoResponse;
import com.dnd.spaced.domain.comment.presentation.dto.response.MultiplePopularCommentInfoResponse;
import com.dnd.spaced.global.config.properties.UrlProperties;
import com.dnd.spaced.global.controller.ResponseEntityConst;
import com.dnd.spaced.global.resolver.auth.AuthAccount;
import com.dnd.spaced.global.resolver.auth.AuthAccountInfo;
import com.dnd.spaced.global.resolver.comment.CommentSortCondition;
import com.dnd.spaced.global.resolver.comment.PopularCommentSortCondition;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final UrlProperties urlProperties;
    private final CommentService commentService;

    @PostMapping("/words/{wordId}/comments")
    public ResponseEntity<Void> save(
            @AuthAccount AuthAccountInfo accountInfo,
            @PathVariable Long wordId,
            @RequestBody @Valid CreateCommentRequest request
    ) {
        commentService.save(CommentServiceMapper.ofCreate(accountInfo.email(), wordId, request.content()));

        return ResponseEntity.created(URI.create("/words" + wordId))
                             .build();
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<Void> update(
            @AuthAccount AuthAccountInfo accountInfo,
            @PathVariable Long commentId,
            @RequestBody @Valid UpdateCommentRequest request
    ) {
        commentService.update(CommentServiceMapper.ofUpdate(accountInfo.email(), commentId, request.content()));

        return ResponseEntityConst.NO_CONTENT;
    }

    @DeleteMapping("/words/{wordId}/comments/{commentId}")
    public ResponseEntity<Void> delete(
            @AuthAccount AuthAccountInfo accountInfo,
            @PathVariable Long wordId,
            @PathVariable Long commentId
    ) {
        commentService.delete(CommentServiceMapper.of(accountInfo.email(), wordId, commentId));

        return ResponseEntityConst.NO_CONTENT;
    }

    @PostMapping("/comments/{commentId}/like")
    public ResponseEntity<Void> processLike(
            @AuthAccount AuthAccountInfo accountInfo,
            @PathVariable Long commentId
    ) {
        commentService.processLike(accountInfo.email(), commentId);

        return ResponseEntityConst.NO_CONTENT;
    }

    @GetMapping("/words/{wordId}/comments")
    public ResponseEntity<MultipleCommentInfoResponse> findAllBy(
            @AuthAccount(required = false) AuthAccountInfo accountInfo,
            @PathVariable Long wordId,
            CommentConditionRequest request,
            @CommentSortCondition Pageable pageable
    ) {
        CommentConditionInfoDto dto = CommentServiceMapper.to(
                wordId,
                request.lastCommentId(),
                request.lastLikeCount(),
                pageable,
                accountInfo.email()
        );
        List<MultipleCommentInfoDto> result = commentService.findAllBy(dto);

        return ResponseEntity.ok(CommentControllerMapper.ofComments(result, urlProperties));
    }

    @GetMapping("/comments/popular")
    public ResponseEntity<MultiplePopularCommentInfoResponse> findPopularAllBy(
            @AuthAccount(required = false) AuthAccountInfo accountInfo,
            @PopularCommentSortCondition Pageable pageable
    ) {
        List<MultiplePopularCommentInfoDto> result = commentService.findPopularAllBy(pageable, accountInfo.email());

        return ResponseEntity.ok(CommentControllerMapper.ofPopularComments(result, urlProperties));
    }
}
