package com.dnd.spaced.domain.word.domain;

import com.dnd.spaced.domain.word.domain.exception.InvalidPronunciationException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pronunciation {

    private String english;

    @Builder
    public Pronunciation(String english) {
        validatePronunciation(english);

        this.english = english;
    }

    private void validatePronunciation(String english) {
        if (isInvalidPronunciation(english)) {
            throw new InvalidPronunciationException();
        }
    }

    private boolean isInvalidPronunciation(String target) {
        return target == null || target.isBlank();
    }
}
