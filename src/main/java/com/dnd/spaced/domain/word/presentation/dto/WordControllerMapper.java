package com.dnd.spaced.domain.word.presentation.dto;

import com.dnd.spaced.domain.word.application.dto.response.DetailWordInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.InputWordCandidateDto;
import com.dnd.spaced.domain.word.application.dto.response.MultipleWordInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.ListWordInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.PopularWordInfoDto;
import com.dnd.spaced.domain.word.application.dto.response.WordSearchInfoDto;
import com.dnd.spaced.domain.word.presentation.dto.response.*;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WordControllerMapper {

    public static MultipleWordInfoResponse to(List<MultipleWordInfoDto> dtos) {
        return MultipleWordInfoResponse.from(dtos);
    }

    public static ListWordInfoResponse too(List<ListWordInfoDto> dtos) {
        return ListWordInfoResponse.from(dtos);
    }

    public static MultipleSearchWordInfoResponse toResponse(List<WordSearchInfoDto> dtos) {
        return MultipleSearchWordInfoResponse.from(dtos);
    }

    public static DetailWordInfoResponse to(DetailWordInfoDto dto) {
        return DetailWordInfoResponse.from(dto);
    }

    public static InputWordCandidateResponse to(InputWordCandidateDto dto) {
        return new InputWordCandidateResponse(dto.candidates());
    }

    public static PopularWordResponse from(List<PopularWordInfoDto> dtos) {
        return PopularWordResponse.from(dtos);
    }

    public static SimpleListWordInfoResponse toSimpleResponse(List<ListWordInfoDto> dtos) {
        return SimpleListWordInfoResponse.from(dtos);
    }
}
