package com.dnd.spaced.domain.bookmark.application.dto;

import com.dnd.spaced.domain.bookmark.application.dto.request.BookmarkConditionInfoDto;
import com.dnd.spaced.domain.bookmark.application.dto.response.BookmarkWordInfoDto;
import com.dnd.spaced.domain.bookmark.domain.repository.dto.response.BookmarkWordDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookmarkServiceMapper {

    public static List<BookmarkWordInfoDto> from(List<BookmarkWordDto> dtos) {
        return dtos.stream()
                    .map(BookmarkWordInfoDto::from)
                    .toList();
    }

    public static BookmarkConditionInfoDto of(String email, Long lastBookmarkId, Pageable pageable) {
        return new BookmarkConditionInfoDto(email, lastBookmarkId, pageable);
    }
}
