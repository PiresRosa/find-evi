package com.dnd.spaced.domain.word.application;

import com.dnd.spaced.domain.account.domain.Account;
import com.dnd.spaced.domain.account.domain.repository.AccountRepository;
import com.dnd.spaced.domain.word.application.dto.WordServiceMapper;
import com.dnd.spaced.domain.word.application.dto.request.SearchWordConditionInfoDto;
import com.dnd.spaced.domain.word.application.dto.request.WordConditionInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.DetailWordInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.InputWordCandidateDto;
import com.dnd.spaced.domain.word.application.dto.response.ListWordInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.PopularWordInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.WordSearchInfoDto;
import com.dnd.spaced.domain.word.application.event.dto.request.FoundWordPopularViewCountEvent;
import com.dnd.spaced.domain.word.application.event.dto.request.FoundWordViewCountEvent;
import com.dnd.spaced.domain.word.application.exception.WordNotFoundException;
import com.dnd.spaced.domain.word.domain.Word;
import com.dnd.spaced.domain.word.domain.repository.PopularWordRepository;
import com.dnd.spaced.domain.word.domain.repository.WordRepository;
import com.dnd.spaced.domain.word.domain.repository.dto.WordRepositoryMapper;
import com.dnd.spaced.domain.word.domain.repository.dto.request.WordConditionDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordCandidateDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordInfoWithBookmarkDto;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WordService {

    private static final long UNAUTHORIZED_ACCOUNT_ID = -1L;

    private final Clock clock;
    private final WordRepository wordRepository;
    private final AccountRepository accountRepository;
    private final PopularWordRepository popularWordRepository;
    private final ApplicationEventPublisher eventPublisher;

    public List<ListWordInfoDto> findAllBy(WordConditionInfoDto dto) {
        WordConditionDto wordConditionDto = WordRepositoryMapper.to(
                dto.categoryName(),
                dto.lastWordName(),
                dto.pageable()
        );
        List<Word> result = wordRepository.findAllBy(wordConditionDto);

        return WordServiceMapper.to(result);
    }

    public DetailWordInfoDto findBy(String email, Long wordId) {
        if (email != null) {
            Long accountId = accountRepository.findBy(email)
                    .map(Account::getId)
                    .orElse(UNAUTHORIZED_ACCOUNT_ID);
            WordInfoWithBookmarkDto result = wordRepository.findWithBookmarkBy(wordId, accountId).orElseThrow(WordNotFoundException::new);

            eventPublisher.publishEvent(new FoundWordViewCountEvent(wordId));
            eventPublisher.publishEvent(new FoundWordPopularViewCountEvent(wordId));

            return WordServiceMapper.to(result);
        } else {
            Word result = wordRepository.findBy(wordId).orElseThrow(WordNotFoundException::new);

            eventPublisher.publishEvent(new FoundWordViewCountEvent(wordId));
            eventPublisher.publishEvent(new FoundWordPopularViewCountEvent(wordId));

            return WordServiceMapper.to(result);
        }
    }

    public InputWordCandidateDto findCandidateAllBy(String target) {
        WordCandidateDto result = wordRepository.findCandidateAllBy(target);

        return WordServiceMapper.from(result);
    }

    public List<WordSearchInfoDto> search(SearchWordConditionInfoDto dto) {
        List<Word> results = wordRepository.searchWords(WordRepositoryMapper.to(dto));

        return WordServiceMapper.toWordSearchResponse(results);
    }

    public List<PopularWordInfoDto> findPopularAll(Pageable pageable) {
        List<Word> result = popularWordRepository.findAllBy(LocalDateTime.now(clock), pageable);

        return WordServiceMapper.from(result);
    }
}
