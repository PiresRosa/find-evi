 package com.dnd.spaced.domain.admin.application;

import com.dnd.spaced.domain.admin.application.dto.request.AdminWordRequestDto;
import com.dnd.spaced.domain.admin.presentation.dto.response.AdminWordResponse;
import com.dnd.spaced.domain.word.domain.Category;
import com.dnd.spaced.domain.word.domain.Word;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminServiceMapper {
    public static Word fromCreateRequest(AdminWordRequestDto dto) {
        return Word.builder()
                .name(dto.name())
                .englishPronunciation(dto.pronunciation().getEnglish())
                .meaning(dto.meaning())
                .categoryName(String.valueOf(Category.findBy(dto.category())))
                .example(dto.example())
                .build();
    }

    public static AdminWordResponse toResponseDto(Word word) {
        return new AdminWordResponse(
                word.getId(),
                word.getName(),
                word.getPronunciation(),
                word.getMeaning(),
                word.getCategory().name(),
                word.getExample()
        );
    }
}
