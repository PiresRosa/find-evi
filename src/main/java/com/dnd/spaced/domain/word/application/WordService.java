package com.dnd.spaced.domain.word.application;

import com.dnd.spaced.domain.account.domain.Account;
import com.dnd.spaced.domain.account.domain.repository.AccountRepository;
import com.dnd.spaced.domain.word.application.dto.WordServiceMapper;
import com.dnd.spaced.domain.word.application.dto.request.SearchWordConditionInfoDto;
import com.dnd.spaced.domain.word.application.dto.request.WordConditionInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.DetailWordInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.InputWordCandidateDto;
import com.dnd.spaced.domain.word.application.dto.response.MultipleWordInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.WordSearchInfoDto;
import com.dnd.spaced.domain.word.application.event.dto.request.FoundWordInfoEvent;
import com.dnd.spaced.domain.word.application.exception.WordNotFoundException;
import com.dnd.spaced.domain.word.domain.Word;
import com.dnd.spaced.domain.word.domain.repository.WordRepository;
import com.dnd.spaced.domain.word.domain.repository.dto.WordRepositoryMapper;
import com.dnd.spaced.domain.word.domain.repository.dto.request.WordConditionDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordCandidateDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordInfoWithBookmarkDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WordService {

    private static final long UNAUTHORIZED_ACCOUNT_ID = -1L;

    private final WordRepository wordRepository;
    private final AccountRepository accountRepository;
    private final ApplicationEventPublisher eventPublisher;

    public List<MultipleWordInfoDto> findAllBy(WordConditionInfoDto dto) {
        WordConditionDto wordConditionDto = WordRepositoryMapper.to(
                dto.categoryName(),
                dto.lastWordName(),
                dto.pageable()
        );
        List<Word> result = wordRepository.findAllBy(wordConditionDto);

        return WordServiceMapper.to(result);
    }

    public DetailWordInfoDto findBy(String email, Long wordId) {
        Long accountId = accountRepository.findBy(email)
                                          .map(Account::getId)
                                          .orElse(UNAUTHORIZED_ACCOUNT_ID);
        WordInfoWithBookmarkDto result = wordRepository.findWithBookmarkBy(wordId, accountId).orElseThrow(WordNotFoundException::new);

        eventPublisher.publishEvent(new FoundWordInfoEvent(wordId));

        return WordServiceMapper.to(result);
    }

    public InputWordCandidateDto findCandidateAllBy(String target) {
        WordCandidateDto result = wordRepository.findCandidateAllBy(target);

        return WordServiceMapper.from(result);
    }

    public List<WordSearchInfoDto> search(SearchWordConditionInfoDto dto) {
        List<Word> results = wordRepository.searchWords(WordRepositoryMapper.to(dto));

        return WordServiceMapper.toWordSearchResponse(results);
    }
}
