package com.dnd.spaced.domain.word.application.dto;

import com.dnd.spaced.domain.word.application.dto.request.WordConditionInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.DetailWordInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.InputWordCandidateDto;
import com.dnd.spaced.domain.word.application.dto.response.MultipleWordInfoDto;
import com.dnd.spaced.domain.word.domain.Word;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordCandidateDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordInfoWithBookmarkDto;
import com.dnd.spaced.domain.word.presentation.dto.response.WordSearchInfoResponse;
import com.dnd.spaced.domain.word.presentation.dto.response.WordSearchResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
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

    public static List<MultipleWordInfoDto> to(List<Word> words) {
        return words.stream()
                .map(MultipleWordInfoDto::from)
                .toList();
    }

    public static DetailWordInfoDto to(WordInfoWithBookmarkDto dto) {
        return DetailWordInfoDto.from(dto);
    }

    public static InputWordCandidateDto from(WordCandidateDto dto) {
        return new InputWordCandidateDto(dto.candidates());
    }

    public static WordSearchResponse toWordSearchResponse(List<Word> words, String lastWordName) {
        List<WordSearchInfoResponse> result = words.stream()
                .map(WordServiceMapper::toWordSearchInfoResponse)
                .toList();
        return new WordSearchResponse(result, lastWordName);
    }

    private static WordSearchInfoResponse toWordSearchInfoResponse(Word word) {
        return new WordSearchInfoResponse(
                word.getId(),
                word.getName(),
                new WordSearchInfoResponse.PronunciationInfo(word.getPronunciation().getEnglish()),
                word.getMeaning(),
                word.getCategory().getName(),
                word.getViewCount(),
                word.getCommentCount()
        );
    }
}
