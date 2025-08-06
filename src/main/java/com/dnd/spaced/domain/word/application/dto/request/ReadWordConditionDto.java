package com.dnd.spaced.domain.word.application.dto.request;

import org.springframework.data.domain.Pageable;

public record ReadWordConditionDto(String email, String categoryName, String lastWordName, Pageable pageable) {
}
