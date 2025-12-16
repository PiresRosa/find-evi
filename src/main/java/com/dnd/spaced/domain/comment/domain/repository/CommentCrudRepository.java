package com.dnd.spaced.domain.comment.domain.repository;

import com.dnd.spaced.domain.comment.domain.Comment;
import org.springframework.data.repository.CrudRepository;

interface CommentCrudRepository extends CrudRepository<Comment, Long> {
}
