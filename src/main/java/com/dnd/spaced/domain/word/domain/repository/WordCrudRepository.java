package com.dnd.spaced.domain.word.domain.repository;

import com.dnd.spaced.domain.word.domain.Word;
import org.springframework.data.repository.CrudRepository;

interface WordCrudRepository extends CrudRepository<Word, Long> {
}
