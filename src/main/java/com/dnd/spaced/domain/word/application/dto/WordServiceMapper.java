package com.dnd.spaced.domain.word.application.dto;

import com.dnd.spaced.domain.word.application.dto.request.WordConditionInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.DetailWordInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.InputWordCandidateDto;
import com.dnd.spaced.domain.word.application.dto.response.MultipleWordInfoDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordCandidateDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordInfoWithBookmarkDto;

import java.util.List;

import com.dnd.spaced.domain.word.domain.repository.dto.response.WordSearchDto;
import com.dnd.spaced.domain.word.presentation.dto.response.WordSearchResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WordServiceMapper {

    public static WordConditionInfoDto to(
            String email,
            String categoryName,
            String lastWordName,
            Pageable pageable
    ) {
        return new WordConditionInfoDto(email, categoryName, lastWordName, pageable);
    }

    public static List<MultipleWordInfoDto> to(List<WordInfoWithBookmarkDto> dtos) {
        return dtos.stream()
                .map(MultipleWordInfoDto::from)
                .toList();
    }

    public static DetailWordInfoDto to(WordInfoWithBookmarkDto dto) {
        return DetailWordInfoDto.from(dto);
    }

    public static InputWordCandidateDto from(WordCandidateDto dto) {
        return new InputWordCandidateDto(dto.candidates());
    }

    public static WordSearchResponse to(Page<WordSearchDto> page) {
        return new WordSearchResponse(page.getContent(), page.getTotalElements());
    }

    public static WordSearchResponse search(List<WordSearchDto> wordSearchDtos) {
        return new WordSearchResponse(wordSearchDtos, wordSearchDtos.size());
    }
}
