package com.dnd.spaced.domain.bookmark.presentation;

import com.dnd.spaced.domain.bookmark.application.BookmarkService;
import com.dnd.spaced.domain.bookmark.application.dto.BookmarkServiceMapper;
import com.dnd.spaced.domain.bookmark.application.dto.request.BookmarkConditionInfoDto;
import com.dnd.spaced.domain.bookmark.application.dto.response.BookmarkWordInfoDto;
import com.dnd.spaced.domain.bookmark.presentation.dto.BookmarkControllerMapper;
import com.dnd.spaced.domain.bookmark.presentation.dto.request.BookmarkConditionRequest;
import com.dnd.spaced.domain.bookmark.presentation.dto.response.BookmarkWordResponse;
import com.dnd.spaced.global.controller.ResponseEntityConst;
import com.dnd.spaced.global.resolver.auth.AuthAccount;
import com.dnd.spaced.global.resolver.auth.AuthAccountInfo;
import com.dnd.spaced.global.resolver.bookmark.BookmarkSortCondition;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookmarkController implements SwaggerBookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/words/{id}/bookmark")
    public ResponseEntity<Void> processBookmark(@AuthAccount AuthAccountInfo accountInfo, @PathVariable Long id) {
        bookmarkService.processBookmark(accountInfo.email(), id);

        return ResponseEntityConst.NO_CONTENT;
    }

    @GetMapping("/bookmarks")
    public ResponseEntity<BookmarkWordResponse> findAllBy(
            @AuthAccount AuthAccountInfo accountInfo,
            BookmarkConditionRequest request,
            @BookmarkSortCondition Pageable pageable
    ) {
        BookmarkConditionInfoDto dto = BookmarkServiceMapper.of(
                accountInfo.email(),
                request.lastBookmarkId(),
                pageable
        );
        List<BookmarkWordInfoDto> result = bookmarkService.findAllBy(dto);

        return ResponseEntity.ok(BookmarkControllerMapper.from(result));
    }
}
