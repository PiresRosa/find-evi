package com.dnd.spaced.domain.account.domain;

import com.dnd.spaced.domain.account.domain.exception.InvalidExperienceException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Experience {

    UNDER_FIRST("1년 차 미만"),
    BETWEEN_FIRST_SECOND("1~2년 차"),
    BETWEEN_SECOND_THIRD("2~3년 차"),
    BETWEEN_THIRD_FOURTH("3~4년 차"),
    BETWEEN_FOURTH_FIFTH("4~5년 차"),
    OVER_FIFTH("5년 차 이상"),
    BLIND("비공개")
    ;

    private final String name;

    Experience(String name) {
        this.name = name;
    }

    public static Experience find(String name) {
        return Arrays.stream(Experience.values())
                     .filter(experience -> experience.name.equals(name))
                     .findAny()
                     .orElseThrow(InvalidExperienceException::new);
    }
}
