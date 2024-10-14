package com.dnd.spaced.domain.account.domain.repository;

import com.dnd.spaced.domain.account.domain.NicknameMetadata;
import org.springframework.data.repository.CrudRepository;

interface NicknameMetadataCrudRepository extends CrudRepository<NicknameMetadata, String> {
}
