package com.dnd.spaced.domain.word.application.dto;

import com.dnd.spaced.domain.word.application.dto.request.SearchWordConditionInfoDto;
import com.dnd.spaced.domain.word.application.dto.request.WordConditionInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.DetailWordInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.InputWordCandidateDto;
import com.dnd.spaced.domain.word.application.dto.response.ListWordInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.PopularWordInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.WordSearchInfoDto;
import com.dnd.spaced.domain.word.domain.Word;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordCandidateDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordInfoWithBookmarkDto;
import com.dnd.spaced.domain.word.presentation.dto.request.WordSearchRequest;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WordServiceMapper {

    public static WordConditionInfoDto to(
            String categoryName,
            String lastWordName,
            Pageable pageable
    ) {
        return new WordConditionInfoDto(categoryName, lastWordName, pageable);
    }

    public static List<ListWordInfoDto> to(List<Word> words) {
        return words.stream()
                    .map(ListWordInfoDto::from)
                    .toList();
    }

    public static DetailWordInfoDto to(WordInfoWithBookmarkDto dto) {
        return DetailWordInfoDto.from(dto);
    }

    public static DetailWordInfoDto to(Word word) {
        return DetailWordInfoDto.from(word);
    }

    public static InputWordCandidateDto from(WordCandidateDto dto) {
        return new InputWordCandidateDto(dto.candidates());
    }

    public static List<WordSearchInfoDto> toWordSearchResponse(List<Word> words) {
        return words.stream()
                    .map(WordServiceMapper::toWordSearchInfoResponse)
                    .toList();
    }

    public static SearchWordConditionInfoDto of(WordSearchRequest request, Pageable pageable) {
        return new SearchWordConditionInfoDto(
                request.name(),
                request.pronunciation(),
                request.lastWordName(),
                request.category(),
                pageable
        );
    }

    public static List<PopularWordInfoDto> from(List<Word> words) {
        return words.stream()
                    .map(word -> new PopularWordInfoDto(word.getId(), word.getName()))
                    .toList();
    }

    private static WordSearchInfoDto toWordSearchInfoResponse(Word word) {
        return new WordSearchInfoDto(
                word.getId(),
                word.getName(),
                new WordSearchInfoDto.PronunciationInfo(word.getPronunciation().getEnglish()),
                word.getMeaning(),
                word.getCategory().getName(),
                word.getViewCount(),
                word.getCommentCount()
        );
    }
}
