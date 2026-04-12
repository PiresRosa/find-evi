package com.dnd.spaced.domain.comment.domain.repository;

import com.dnd.spaced.domain.comment.domain.Like;
import org.springframework.data.repository.CrudRepository;

interface LikeCrudRepository extends CrudRepository<Like, Long> {
}
