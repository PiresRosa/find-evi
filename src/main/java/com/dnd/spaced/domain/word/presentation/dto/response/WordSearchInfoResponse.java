package com.dnd.spaced.domain.word.presentation.dto.response;

public record WordSearchInfoResponse(
        Long id,
        String name,
        PronunciationInfo pronunciationInfo,
        String meaning,
        String category,
        Integer viewCount,
        Integer commentCount
) {

    public record PronunciationInfo(String english) {
    }
}
