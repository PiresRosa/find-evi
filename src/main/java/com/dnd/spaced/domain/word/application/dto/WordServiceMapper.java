package com.dnd.spaced.domain.word.application.dto;

import com.dnd.spaced.domain.word.application.dto.request.ReadWordConditionDto;
import com.dnd.spaced.domain.word.application.dto.response.DetailWordInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.InputWordCandidateDto;
import com.dnd.spaced.domain.word.application.dto.response.ReadMultipleWordInfoDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordCandidateDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordWithBookmarkDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WordServiceMapper {

    public static ReadWordConditionDto to(
            String email,
            String categoryName,
            String lastWordName,
            Pageable pageable
    ) {
        return new ReadWordConditionDto(email, categoryName, lastWordName, pageable);
    }

    public static List<ReadMultipleWordInfoDto> to(List<WordWithBookmarkDto> dtos) {
        return dtos.stream()
                   .map(ReadMultipleWordInfoDto::from)
                   .toList();
    }

    public static DetailWordInfoDto to(WordWithBookmarkDto dto) {
        return DetailWordInfoDto.from(dto);
    }

    public static InputWordCandidateDto from(WordCandidateDto dto) {
        return new InputWordCandidateDto(dto.candidates());
    }
}
