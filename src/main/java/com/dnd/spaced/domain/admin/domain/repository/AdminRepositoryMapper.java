package com.dnd.spaced.domain.admin.domain.repository;

import com.dnd.spaced.domain.admin.domain.repository.dto.request.AdminWordConditionDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminRepositoryMapper {

    public static AdminWordConditionDto to(String categoryName, String lastWordName, Pageable pageable) {
        return new AdminWordConditionDto(categoryName, lastWordName, pageable);
    }
}
