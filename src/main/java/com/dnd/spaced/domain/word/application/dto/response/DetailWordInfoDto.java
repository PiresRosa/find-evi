package com.dnd.spaced.domain.word.application.dto.response;

import com.dnd.spaced.domain.word.domain.Word;
import com.dnd.spaced.domain.word.domain.repository.dto.response.WordInfoWithBookmarkDto;
import java.time.LocalDateTime;

public record DetailWordInfoDto(
        Long id,
        String name,
        PronunciationInfoDto pronunciationInfo,
        String meaning,
        String category,
        int viewCount,
        int commentCount,
        int bookmarkCount,
        String example,
        boolean isMarked,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static DetailWordInfoDto from(WordInfoWithBookmarkDto dto) {
        return new DetailWordInfoDto(
                dto.wordId(),
                dto.name(),
                new PronunciationInfoDto(dto.pronunciation().getEnglish()),
                dto.meaning(),
                dto.category().getName(),
                dto.viewCount() + 1,
                dto.commentCount(),
                dto.bookmarkCount(),
                dto.example(),
                dto.bookmarkId() != null,
                dto.createdAt(),
                dto.updatedAt()
        );
    }

    public static DetailWordInfoDto from(Word word) {
        return new DetailWordInfoDto(
                word.getId(),
                word.getName(),
                new PronunciationInfoDto(word.getPronunciation().getEnglish()),
                word.getMeaning(),
                word.getCategory().getName(),
                word.getViewCount() + 1,
                word.getCommentCount(),
                word.getBookmarkCount(),
                word.getExample(),
                false,
                word.getCreatedAt(),
                word.getUpdatedAt()
        );
    }


    public record PronunciationInfoDto(String english) {
    }
}
