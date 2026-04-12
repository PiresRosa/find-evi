package com.dnd.spaced.domain.comment.domain.repository;

import com.dnd.spaced.domain.comment.domain.Like;
import java.util.Optional;

public interface LikeRepository {

    void save(Like like);

    void delete(Like like);

    Optional<Like> findBy(Long accountId, Long commentId);
}
