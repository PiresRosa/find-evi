package com.dnd.spaced.domain.word.presentation;

import com.dnd.spaced.domain.word.application.WordService;
import com.dnd.spaced.domain.word.application.dto.WordServiceMapper;
import com.dnd.spaced.domain.word.application.dto.response.DetailWordInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.InputWordCandidateDto;
import com.dnd.spaced.domain.word.application.dto.response.MultipleWordInfoDto;
import com.dnd.spaced.domain.word.presentation.dto.WordControllerMapper;
import com.dnd.spaced.domain.word.presentation.dto.request.MultipleWordConditionRequest;
import com.dnd.spaced.domain.word.presentation.dto.request.WordSearchRequest;
import com.dnd.spaced.domain.word.presentation.dto.response.DetailWordInfoResponse;
import com.dnd.spaced.domain.word.presentation.dto.response.InputWordCandidateResponse;
import com.dnd.spaced.domain.word.presentation.dto.response.MultipleWordInfoResponse;
import com.dnd.spaced.domain.word.presentation.dto.response.WordSearchResponse;
import com.dnd.spaced.global.controller.ResponseEntityConst;
import com.dnd.spaced.global.resolver.auth.AuthAccount;
import com.dnd.spaced.global.resolver.auth.AuthAccountInfo;
import com.dnd.spaced.global.resolver.word.WordSortCondition;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/words")
@RequiredArgsConstructor
public class WordController {

    private final WordService wordService;

    @GetMapping
    public ResponseEntity<MultipleWordInfoResponse> findAllBy(
            @AuthAccount(required = false) AuthAccountInfo accountInfo,
            MultipleWordConditionRequest request,
            @WordSortCondition Pageable pageable
    ) {
        List<MultipleWordInfoDto> result = wordService.findAllBy(
                WordServiceMapper.to(
                        accountInfo.email(),
                        request.categoryName(),
                        request.lastWordName(),
                        pageable
                )
        );

        return ResponseEntity.ok(WordControllerMapper.to(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailWordInfoResponse> findBy(
            @AuthAccount(required = false) AuthAccountInfo accountInfo,
            @PathVariable Long id
    ) {
        DetailWordInfoDto result = wordService.findBy(accountInfo.email(), id);

        return ResponseEntity.ok(WordControllerMapper.to(result));
    }

    @PostMapping("/{id}/bookmark")
    public ResponseEntity<Void> processBookmark(
            @AuthAccount AuthAccountInfo accountInfo,
            @PathVariable Long id
    ) {
        wordService.processBookmark(accountInfo.email(), id);

        return ResponseEntityConst.NO_CONTENT;
    }

    @GetMapping("/candidates")
    public ResponseEntity<InputWordCandidateResponse> findCandidateAllBy(String name) {
        InputWordCandidateDto result = wordService.findCandidateAllBy(name);

        return ResponseEntity.ok(WordControllerMapper.to(result));
    }

    @GetMapping("/search")
    public ResponseEntity<WordSearchResponse> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String pronunciation,
            @RequestParam(required = false) String lastWordName,
            @RequestParam(defaultValue = "전체") String category,
            Pageable pageable
    ) {
        WordSearchRequest request = new WordSearchRequest(name, pronunciation, lastWordName, category, pageable);
        return ResponseEntity.ok(wordService.search(request));
    }
}
