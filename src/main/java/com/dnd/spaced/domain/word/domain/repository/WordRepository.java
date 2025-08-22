package com.dnd.spaced.domain.word.domain.repository;

import com.dnd.spaced.domain.word.domain.Word;
import com.dnd.spaced.domain.word.domain.repository.dto.request.WordConditionDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordCandidateDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordInfoWithBookmarkDto;
import java.util.List;
import java.util.Optional;

public interface WordRepository {

    Optional<Word> findBy(Long wordId);

    Optional<WordInfoWithBookmarkDto> findWithBookmarkBy(Long wordId, Long accountId);

    List<WordInfoWithBookmarkDto> findAllBy(WordConditionDto wordConditionDto, Long accountId);

    WordCandidateDto findCandidateAllBy(String target);
}
