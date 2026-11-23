package com.dnd.spaced.domain.word.domain.repository;

import com.dnd.spaced.domain.word.domain.Word;
import com.dnd.spaced.domain.word.domain.repository.dto.request.WordConditionDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordCandidateDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordInfoWithBookmarkDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordSearchDto;
import com.dnd.spaced.domain.word.presentation.dto.request.WordSearchRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface WordRepository {

    Optional<Word> findBy(Long wordId);

    Optional<WordInfoWithBookmarkDto> findWithBookmarkBy(Long wordId, Long accountId);

    List<WordInfoWithBookmarkDto> findAllBy(WordConditionDto wordConditionDto, Long accountId);

    WordCandidateDto findCandidateAllBy(String target);

    void updateViewCount(Long wordId);

    Page<WordSearchDto> searchWords(WordSearchRequest request, Long accountId);
}
