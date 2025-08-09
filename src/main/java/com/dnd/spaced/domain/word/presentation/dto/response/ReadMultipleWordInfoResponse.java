package com.dnd.spaced.domain.word.presentation.dto.response;

import com.dnd.spaced.domain.word.application.dto.response.ReadMultipleWordInfoDto;
import java.util.List;

public record ReadMultipleWordInfoResponse(List<WordInfoResponse> words, String lastWordName) {

    public static ReadMultipleWordInfoResponse from(List<ReadMultipleWordInfoDto> dtos) {
        List<WordInfoResponse> words = dtos.stream()
                                           .map(WordInfoResponse::from)
                                           .toList();

        return new ReadMultipleWordInfoResponse(words, words.get(words.size() - 1).name());
    }
}
