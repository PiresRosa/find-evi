package com.dnd.spaced.domain.bookmark.application;

import com.dnd.spaced.domain.account.domain.Account;
import com.dnd.spaced.domain.account.domain.repository.AccountRepository;
import com.dnd.spaced.domain.bookmark.domain.Bookmark;
import com.dnd.spaced.domain.bookmark.domain.repository.BookmarkRepository;
import com.dnd.spaced.domain.word.application.exception.ForbiddenBookmarkException;
import com.dnd.spaced.domain.word.application.exception.WordNotFoundException;
import com.dnd.spaced.domain.word.domain.Word;
import com.dnd.spaced.domain.word.domain.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final WordRepository wordRepository;
    private final AccountRepository accountRepository;
    private final BookmarkRepository bookmarkRepository;

    public void processBookmark(String email, Long wordId) {
        Account account = accountRepository.findBy(email).orElseThrow(ForbiddenBookmarkException::new);
        Word word = wordRepository.findBy(wordId).orElseThrow(WordNotFoundException::new);

        bookmarkRepository.findBy(account.getId(), word.getId()).ifPresentOrElse(bookmarkRepository::delete, () -> {
            Bookmark bookmark = Bookmark.builder().accountId(account.getId()).wordId(word.getId()).build();

            bookmarkRepository.save(bookmark);
        });
    }
}
