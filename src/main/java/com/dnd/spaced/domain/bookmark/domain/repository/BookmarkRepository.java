package com.dnd.spaced.domain.bookmark.domain.repository;

import com.dnd.spaced.domain.bookmark.domain.Bookmark;
import com.dnd.spaced.domain.bookmark.domain.repository.dto.request.BookmarkConditionDto;
import com.dnd.spaced.domain.bookmark.domain.repository.dto.response.BookmarkWordDto;
import java.util.List;
import java.util.Optional;

public interface BookmarkRepository {

    Bookmark save(Bookmark bookmark);

    void delete(Bookmark bookmark);

    Optional<Bookmark> findBy(Long accountId, Long wordId);

    List<BookmarkWordDto> findAllBy(BookmarkConditionDto dto);
}
