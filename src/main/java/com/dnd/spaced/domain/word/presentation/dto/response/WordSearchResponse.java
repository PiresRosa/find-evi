package com.dnd.spaced.domain.word.presentation.dto.response;

import com.dnd.spaced.domain.word.domain.repository.dto.response.WordSearchDto;

import java.util.List;

public record WordSearchResponse(
        List<WordSearchDto> words,
        String lastWordName
) {
}
