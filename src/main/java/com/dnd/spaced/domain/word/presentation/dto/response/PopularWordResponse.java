package com.dnd.spaced.domain.word.presentation.dto.response;

import com.dnd.spaced.domain.word.application.dto.response.PopularWordInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record PopularWordResponse(@Schema(description = "용어 정보") List<PopularWordInfoResponse> words) {

    public static PopularWordResponse from(List<PopularWordInfoDto> dtos) {
        List<PopularWordInfoResponse> words = dtos.stream()
                                                  .map(dto -> new PopularWordInfoResponse(dto.id(), dto.name()))
                                                  .toList();

        return new PopularWordResponse(words);
    }

    private record PopularWordInfoResponse(

            @Schema(description = "용어 ID")
            Long id,

            @Schema(description = "용어 이름")
            String name
    ) {
    }
}
