package com.dnd.spaced.domain.word.domain.repository;

import com.dnd.spaced.domain.word.domain.Bookmark;
import org.springframework.data.repository.CrudRepository;

interface BookmarkCrudRepository extends CrudRepository<Bookmark, Long> {
}
