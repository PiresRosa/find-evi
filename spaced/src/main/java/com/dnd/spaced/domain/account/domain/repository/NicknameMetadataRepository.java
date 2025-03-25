package com.dnd.spaced.domain.account.domain.repository;

import com.dnd.spaced.domain.account.domain.NicknameMetadata;
import java.util.Optional;

public interface NicknameMetadataRepository {

    NicknameMetadata save(NicknameMetadata nicknameMetadata);

    Optional<NicknameMetadata> findBy(String nickname);
}
