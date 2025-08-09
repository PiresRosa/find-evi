package com.dnd.spaced.domain.word.presentation.dto.response;

import com.dnd.spaced.domain.word.application.dto.response.ReadMultipleWordInfoDto;

public record WordInfoResponse(
        Long id,
        String name,
        PronunciationInfoResponse pronunciationInfo,
        String meaning,
        String category,
        int viewCount,
        boolean isMarked
) {

    public record PronunciationInfoResponse(String korean, String english) {
    }

    public static WordInfoResponse from(ReadMultipleWordInfoDto dto) {
        return new WordInfoResponse(
                dto.id(),
                dto.name(),
                new PronunciationInfoResponse(dto.pronunciationInfo().korean(), dto.pronunciationInfo().english()),
                dto.meaning(),
                dto.category(),
                dto.viewCount(),
                dto.isMarked()
        );
    }
}
