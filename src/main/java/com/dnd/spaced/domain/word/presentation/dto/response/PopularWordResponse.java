package com.dnd.spaced.domain.word.presentation.dto.response;

import com.dnd.spaced.domain.word.application.dto.response.PopularWordInfoDto;
import java.util.List;

public record PopularWordResponse(List<WordInfoResponse> words) {

    public static PopularWordResponse from(List<PopularWordInfoDto> dtos) {
        List<WordInfoResponse> words = dtos.stream()
                                           .map(dto -> new WordInfoResponse(dto.id(), dto.name()))
                                           .toList();

        return new PopularWordResponse(words);
    }

    public record WordInfoResponse(Long id, String name) {

    }
}
