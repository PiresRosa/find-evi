package com.dnd.spaced.domain.word.presentation.dto.response;

import java.util.List;

public record WordSearchResponse(
        List<WordSearchInfoResponse> words,
        String lastWordName
) {
}
