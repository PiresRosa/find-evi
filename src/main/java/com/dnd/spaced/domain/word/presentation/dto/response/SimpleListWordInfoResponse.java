package com.dnd.spaced.domain.word.presentation.dto.response;

import com.dnd.spaced.domain.word.application.dto.response.ListWordInfoDto;

import java.util.List;

public record SimpleListWordInfoResponse(
        List<SimpleWordInfoResponse> words
) {
    public static SimpleListWordInfoResponse from(List<ListWordInfoDto> dtos) {
        List<SimpleWordInfoResponse> words = SimpleWordInfoResponse.from(dtos);
        return new SimpleListWordInfoResponse(words);
    }
}
