package com.dnd.spaced.domain.word.presentation.dto.response;

import com.dnd.spaced.domain.word.application.dto.response.WordSearchInfoDto;
import java.util.Collections;
import java.util.List;

public record MultipleSearchWordInfoResponse(List<WordSearchInfoResponse> words, String lastWordName) {

    public static MultipleSearchWordInfoResponse from(List<WordSearchInfoDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new MultipleSearchWordInfoResponse(Collections.emptyList(), null);
        }

        List<WordSearchInfoResponse> words = dtos.stream()
                                                 .map(WordSearchInfoResponse::from)
                                                 .toList();

        return new MultipleSearchWordInfoResponse(words, words.get(words.size() - 1).name());
    }
}
