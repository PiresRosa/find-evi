 package com.dnd.spaced.domain.admin.application;

import com.dnd.spaced.domain.admin.application.dto.request.AdminWordConditionInfoDto;
import com.dnd.spaced.domain.admin.application.dto.request.AdminWordRequestDto;
import com.dnd.spaced.domain.admin.application.dto.response.ReportInfoDto;
import com.dnd.spaced.domain.admin.presentation.dto.response.AdminWordResponse;
import com.dnd.spaced.domain.report.domain.Report;
import com.dnd.spaced.domain.word.domain.Category;
import com.dnd.spaced.domain.word.domain.Word;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminServiceMapper {

     public static AdminWordConditionInfoDto to(
             String categoryName,
             String lastWordName,
             Pageable pageable
     ) {
         return new AdminWordConditionInfoDto(categoryName, lastWordName, pageable);
     }

    public static List<AdminWordResponse> toAdminWordResponseList(List<Word> words) {
        return words.stream()
                .map(AdminServiceMapper::toResponseDto)
                .toList();
    }

    public static Word fromCreateRequest(AdminWordRequestDto dto) {
        return Word.builder()
                .name(dto.name())
                .englishPronunciation(dto.pronunciation().getEnglish())
                .meaning(dto.meaning())
                .categoryName(Category.findBy(dto.category()).getName())
                .examples(dto.examples())
                .build();
    }

    public static Word fromUpdateRequest(AdminWordRequestDto dto, Word existingWord) {
        existingWord.updateDetails(
                dto.name(),
                dto.pronunciation().getEnglish(),
                dto.meaning(),
                Category.findBy(dto.category()).getName(),
                dto.examples()
        );
        return existingWord;
    }

    public static ReportInfoDto toReportInfoDto(Report report) {
        return new ReportInfoDto(report.getId(), report.getReason().name());
    }

    public static AdminWordResponse toResponseDto(Word word) {
        return new AdminWordResponse(
                word.getId(),
                word.getName(),
                word.getPronunciation(),
                word.getMeaning(),
                word.getCategory().name(),
                word.getExamples()
        );
    }
}
