package com.dnd.spaced.domain.word.domain.repository;

import com.dnd.spaced.domain.word.domain.Bookmark;
import java.util.Optional;

public interface BookmarkRepository {

    Bookmark save(Bookmark bookmark);

    void delete(Bookmark bookmark);

    Optional<Bookmark> findBy(Long accountId, Long wordId);
}
