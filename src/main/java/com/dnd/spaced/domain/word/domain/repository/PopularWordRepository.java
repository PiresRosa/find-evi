package com.dnd.spaced.domain.word.domain.repository;

import com.dnd.spaced.domain.word.domain.PopularWordMetadata;
import com.dnd.spaced.domain.word.domain.PopularWordSchedule;
import com.dnd.spaced.domain.word.domain.Word;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface PopularWordRepository {

    void save(PopularWordMetadata popularWordMetadata);

    void save(PopularWordSchedule popularWordSchedule);

    List<Word> findAllBy(LocalDateTime target, Pageable pageable);

    Optional<PopularWordMetadata> findBy(Long wordId, LocalDateTime target);

    Optional<PopularWordSchedule> findBy(LocalDateTime target);

    void deleteBy(LocalDateTime target);
}
