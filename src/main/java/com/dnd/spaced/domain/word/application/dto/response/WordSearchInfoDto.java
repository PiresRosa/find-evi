package com.dnd.spaced.domain.word.application.dto.response;

public record WordSearchInfoDto(
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
