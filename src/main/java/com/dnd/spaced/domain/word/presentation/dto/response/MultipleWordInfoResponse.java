package com.dnd.spaced.domain.word.presentation.dto.response;

import com.dnd.spaced.domain.word.application.dto.response.MultipleWordInfoDto;
import java.util.Collections;
import java.util.List;

public record MultipleWordInfoResponse(List<WordInfoResponse> words, String lastWordName) {

    public static MultipleWordInfoResponse from(List<MultipleWordInfoDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new MultipleWordInfoResponse(Collections.emptyList(), null);
        }

        List<WordInfoResponse> words = dtos.stream()
                                           .map(WordInfoResponse::from)
                                           .toList();

        return new MultipleWordInfoResponse(words, words.get(words.size() - 1).name());
    }
}
