package com.dnd.spaced.domain.admin.presentation.dto.response;

import com.dnd.spaced.domain.admin.application.dto.response.AdminListWordInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Collections;
import java.util.List;

public record AdminListWordInfoResponse(

        @Schema(description = "용어 정보")
        List<AdminWordInfoResponse> words,

        @Schema(description = "마지막으로 조회한 용어 이름", nullable = true)
        String lastWordName
) {

    public static AdminListWordInfoResponse from(List<AdminListWordInfoDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new AdminListWordInfoResponse(Collections.emptyList(), null);
        }

        List<AdminWordInfoResponse> words = dtos.stream()
                .map(AdminWordInfoResponse::from)
                .toList();

        return new AdminListWordInfoResponse(words, words.get(words.size() - 1).name());
    }

    private record AdminWordInfoResponse(
            @Schema(description = "용어 ID")
            Long id,

            @Schema(description = "용어 이름")
            String name,

            @Schema(description = "용어 뜻")
            String meaning,

            @Schema(description = "용어 카테고리", allowableValues = {"개발", "비즈니스", "디자인"})
            String category,

            @Schema(description = "댓글 개수")
            int commentCount,

            @Schema(description = "조회수")
            int viewCount
    ) {

        public static AdminWordInfoResponse from(AdminListWordInfoDto dto) {
            return new AdminWordInfoResponse(
                    dto.id(),
                    dto.name(),
                    dto.meaning(),
                    dto.category(),
                    dto.commentCount(),
                    dto.viewCount()
            );
        }
    }
}
