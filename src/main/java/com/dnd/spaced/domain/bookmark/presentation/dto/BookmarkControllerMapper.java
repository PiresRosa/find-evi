package com.dnd.spaced.domain.bookmark.presentation.dto;

import com.dnd.spaced.domain.bookmark.application.dto.response.BookmarkWordInfoDto;
import com.dnd.spaced.domain.bookmark.presentation.dto.response.BookmarkWordResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BookmarkControllerMapper {

    public static BookmarkWordResponse from(List<BookmarkWordInfoDto> dtos) {
        return BookmarkWordResponse.from(dtos);
    }
}
