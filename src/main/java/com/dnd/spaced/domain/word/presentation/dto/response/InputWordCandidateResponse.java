package com.dnd.spaced.domain.word.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record InputWordCandidateResponse(@Schema(description = "용어 이름 검색어 후보군") List<String> candidates) {
}
