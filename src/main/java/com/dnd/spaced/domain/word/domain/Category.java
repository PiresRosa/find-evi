package com.dnd.spaced.domain.word.domain;

import com.dnd.spaced.domain.word.domain.exception.InvalidCategoryException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Category {
    BUSINESS("비즈니스"), DEVELOP("개발"), DESIGN("디자인"), ALL("전체 실무");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public static Category findBy(String name) {
        return Arrays.stream(Category.values())
                     .filter(category -> category.name.equals(name))
                     .findAny()
                     .orElseThrow(InvalidCategoryException::new);
    }
}
