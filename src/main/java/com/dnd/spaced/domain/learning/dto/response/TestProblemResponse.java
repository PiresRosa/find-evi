package com.dnd.spaced.domain.learning.dto.response;

import java.util.List;

public record TestProblemResponse(
        Long id,
        List<ProblemInfoResponse> problemInfo
) {

    public record ProblemInfoResponse(
            Long answerId,
            String question,
            String name,
            String category,
            double correctPercent,
            double wrongPercent,
            List<OptionInfoResponse> optionInfo
    ) {

        public record OptionInfoResponse(
                Long optionWordId,
                String meaning
        ) {
        }
    }
}
