package com.dnd.spaced.domain.word.domain.repository;

import com.dnd.spaced.domain.word.domain.PopularWordMetadata;
import org.springframework.data.repository.CrudRepository;

interface PopularWordMetadataCrudRepository extends CrudRepository<PopularWordMetadata, Long> {
}
