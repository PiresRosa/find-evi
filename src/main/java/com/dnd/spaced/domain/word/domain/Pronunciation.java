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

    private String korean;
    private String english;

    @Builder
    private Pronunciation(String korean, String english) {
        validatePronunciation(korean, english);

        this.korean = korean;
        this.english = english;
    }

    private void validatePronunciation(String korean, String english) {
        if (isInvalidPronunciation(korean) || isInvalidPronunciation(english)) {
            throw new InvalidPronunciationException();
        }
    }

    private boolean isInvalidPronunciation(String target) {
        return target == null || target.isBlank();
    }
}
