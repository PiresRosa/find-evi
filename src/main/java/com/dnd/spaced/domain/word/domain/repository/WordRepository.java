package com.dnd.spaced.domain.word.domain.repository;

import com.dnd.spaced.domain.word.domain.Word;
import com.dnd.spaced.domain.word.domain.repository.dto.request.WordConditionDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordCandidateDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordInfoWithBookmarkDto;
import com.dnd.spaced.domain.word.presentation.dto.request.WordSearchRequest;
import java.util.List;
import java.util.Optional;

public interface WordRepository {

    Optional<Word> findBy(Long wordId);

    Optional<WordInfoWithBookmarkDto> findWithBookmarkBy(Long wordId, Long accountId);

    List<Word> findAllBy(WordConditionDto wordConditionDto, Long accountId);

    WordCandidateDto findCandidateAllBy(String target);

    void updateViewCount(Long wordId);

    List<Word> searchWords(WordSearchRequest request, Long accountId);
}
