package com.dnd.spaced.domain.word.domain.repository.dto.request;

import org.springframework.data.domain.Pageable;

public record WordConditionDto(String categoryName, String lastWordName, Pageable pageable) {
}
