package com.dnd.spaced.domain.word.presentation.dto.response;

import com.dnd.spaced.domain.word.application.dto.response.WordSearchInfoDto;

public record WordSearchInfoResponse(
        Long id,
        String name,
        PronunciationInfoResponse pronunciationInfo,
        String meaning,
        String category,
        Integer viewCount,
        Integer commentCount
) {

    public record PronunciationInfoResponse(String english) {
    }

    public static WordSearchInfoResponse from(WordSearchInfoDto dto) {
        return new WordSearchInfoResponse(
                dto.id(),
                dto.name(),
                new PronunciationInfoResponse(dto.pronunciationInfo().english()),
                dto.meaning(),
                dto.category(),
                dto.viewCount(),
                dto.commentCount()
        );
    }
}
