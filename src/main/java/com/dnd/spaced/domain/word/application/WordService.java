package com.dnd.spaced.domain.word.application;

import com.dnd.spaced.domain.account.domain.Account;
import com.dnd.spaced.domain.account.domain.repository.AccountRepository;
import com.dnd.spaced.domain.word.application.dto.WordServiceMapper;
import com.dnd.spaced.domain.word.application.dto.request.WordConditionInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.DetailWordInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.InputWordCandidateDto;
import com.dnd.spaced.domain.word.application.dto.response.MultipleWordInfoDto;
import com.dnd.spaced.domain.word.application.event.dto.request.FoundWordInfoEvent;
import com.dnd.spaced.domain.word.application.exception.ForbiddenBookmarkException;
import com.dnd.spaced.domain.word.application.exception.WordNotFoundException;
import com.dnd.spaced.domain.word.domain.Bookmark;
import com.dnd.spaced.domain.word.domain.Word;
import com.dnd.spaced.domain.word.domain.repository.BookmarkRepository;
import com.dnd.spaced.domain.word.domain.repository.WordRepository;
import com.dnd.spaced.domain.word.domain.repository.dto.WordRepositoryMapper;
import com.dnd.spaced.domain.word.domain.repository.dto.request.WordConditionDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordCandidateDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordInfoWithBookmarkDto;

import java.util.List;

import com.dnd.spaced.domain.word.domain.repository.dto.response.WordSearchDto;
import com.dnd.spaced.domain.word.presentation.dto.request.WordSearchRequest;
import com.dnd.spaced.domain.word.presentation.dto.response.WordSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WordService {

    private static final long UNAUTHORIZED_ACCOUNT_ID = -1L;

    private final WordRepository wordRepository;
    private final AccountRepository accountRepository;
    private final BookmarkRepository bookmarkRepository;
    private final ApplicationEventPublisher eventPublisher;

    public List<MultipleWordInfoDto> findAllBy(WordConditionInfoDto dto) {
        Long accountId = findAccountId(dto.email());
        WordConditionDto wordConditionDto = WordRepositoryMapper.to(dto.categoryName(), dto.lastWordName(), dto.pageable());
        List<WordInfoWithBookmarkDto> result = wordRepository.findAllBy(wordConditionDto, accountId);

        return WordServiceMapper.to(result);
    }

    public DetailWordInfoDto findBy(String email, Long wordId) {
        Long accountId = findAccountId(email);
        WordInfoWithBookmarkDto result = wordRepository.findWithBookmarkBy(wordId, accountId).orElseThrow(WordNotFoundException::new);

        eventPublisher.publishEvent(new FoundWordInfoEvent(wordId));

        return WordServiceMapper.to(result);
    }

    public void processBookmark(String email, Long wordId) {
        Account account = accountRepository.findBy(email).orElseThrow(ForbiddenBookmarkException::new);
        Word word = wordRepository.findBy(wordId).orElseThrow(WordNotFoundException::new);

        bookmarkRepository.findBy(account.getId(), word.getId()).ifPresentOrElse(bookmarkRepository::delete, () -> {
            Bookmark bookmark = Bookmark.builder().accountId(account.getId()).wordId(word.getId()).build();

            bookmarkRepository.save(bookmark);
        });
    }

    public InputWordCandidateDto findCandidateAllBy(String target) {
        WordCandidateDto result = wordRepository.findCandidateAllBy(target);

        return WordServiceMapper.from(result);
    }

    public WordSearchResponse search(WordSearchRequest request) {
        Page<WordSearchDto> resultPage = wordRepository.searchWords(request);
        validateSearchResults(resultPage);

        return WordServiceMapper.to(resultPage);
    }

    private void validateSearchResults(Page<WordSearchDto> resultPage) {
        if (resultPage.getTotalElements() == 0) {
            throw new WordNotFoundException();
        }
    }

    private Long findAccountId(String email) {
        return accountRepository.findBy(email).map(Account::getId).orElse(UNAUTHORIZED_ACCOUNT_ID);
    }
}
