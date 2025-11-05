package com.dnd.spaced.domain.word.domain;

import com.dnd.spaced.domain.word.domain.exception.InvalidExampleException;
import com.dnd.spaced.domain.word.domain.exception.InvalidMeaningException;
import com.dnd.spaced.domain.word.domain.exception.InvalidNameException;
import com.dnd.spaced.global.entity.BaseTimeEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false, of = "id")
public class Word extends BaseTimeEntity {

    private static final int MIN_MEANING_LENGTH = 10;
    private static final int MAX_MEANING_LENGTH = 70;
    private static final int MIN_EXAMPLE_LENGTH = 1;
    private static final int MAX_EXAMPLE_LENGTH = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Pronunciation pronunciation;

    private String meaning;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String example;

    private int viewCount = 0;

    @Builder
    private Word(
            String name,
            String koreanPronunciation,
            String englishPronunciation,
            String meaning,
            String categoryName,
            String example
    ) {
        validateContent(name, meaning, example);

        this.name = name;
        this.pronunciation = Pronunciation.builder()
                                          .korean(koreanPronunciation)
                                          .english(englishPronunciation)
                                          .build();
        this.meaning = meaning;
        this.category = Category.findBy(categoryName);
        this.example = example;
    }

    private void validateContent(String name, String meaning, String example) {
        if (isInvalidName(name)) {
            throw new InvalidNameException();
        }
        if (isInvalidMeaning(meaning)) {
            throw new InvalidMeaningException();
        }
        if (isInvalidExample(example)) {
            throw new InvalidExampleException();
        }
    }

    private boolean isInvalidName(String name) {
        return name == null || name.isBlank();
    }

    private boolean isInvalidMeaning(String meaning) {
        int length = meaning.length();

        return MIN_MEANING_LENGTH > length || MAX_MEANING_LENGTH < length;
    }

    private boolean isInvalidExample(String example) {
        int length = example.length();

        return MIN_EXAMPLE_LENGTH > length || MAX_EXAMPLE_LENGTH < length;
    }
}
