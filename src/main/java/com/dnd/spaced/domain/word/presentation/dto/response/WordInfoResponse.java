package com.dnd.spaced.domain.word.presentation.dto.response;

import com.dnd.spaced.domain.word.application.dto.response.MultipleWordInfoDto;

public record WordInfoResponse(
        Long id,
        String name,
        PronunciationInfoResponse pronunciationInfo,
        String meaning,
        String category,
        int viewCount
) {

    public record PronunciationInfoResponse(String english) {
    }

    public static WordInfoResponse from(MultipleWordInfoDto dto) {
        return new WordInfoResponse(
                dto.id(),
                dto.name(),
                new PronunciationInfoResponse(dto.pronunciationInfo().english()),
                dto.meaning(),
                dto.category(),
                dto.viewCount()
        );
    }
}
