package com.dnd.spaced.domain.bookmark.domain.repository;

import com.dnd.spaced.domain.bookmark.domain.Bookmark;
import org.springframework.data.repository.CrudRepository;

interface BookmarkCrudRepository extends CrudRepository<Bookmark, Long> {
}
