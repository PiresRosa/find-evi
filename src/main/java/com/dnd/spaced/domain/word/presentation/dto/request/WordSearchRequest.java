package com.dnd.spaced.domain.word.presentation.dto.request;

public record WordSearchRequest(
        String name,
        String pronunciation,
        String lastWordName,
        String category
) {
    public WordSearchRequest {
        if (category == null || category.isBlank()) {
            category = "전체";
        }
    }
}
