package com.dnd.spaced.domain.bookmark.presentation;

import com.dnd.spaced.domain.bookmark.application.BookmarkService;
import com.dnd.spaced.global.controller.ResponseEntityConst;
import com.dnd.spaced.global.resolver.auth.AuthAccount;
import com.dnd.spaced.global.resolver.auth.AuthAccountInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/words/{id}/bookmark")
    public ResponseEntity<Void> processBookmark(
            @AuthAccount AuthAccountInfo accountInfo,
            @PathVariable Long id
    ) {
        bookmarkService.processBookmark(accountInfo.email(), id);

        return ResponseEntityConst.NO_CONTENT;
    }
}
