package com.dnd.spaced.domain.word.domain.repository.dto;

import com.dnd.spaced.domain.word.domain.repository.dto.request.WordConditionDto;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordCandidateDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WordRepositoryMapper {

    public static WordConditionDto to(String categoryName, String lastWordName, Pageable pageable) {
        return new WordConditionDto(categoryName, lastWordName, pageable);
    }

    public static WordCandidateDto to(List<String> candidates) {
        return new WordCandidateDto(candidates);
    }
}
