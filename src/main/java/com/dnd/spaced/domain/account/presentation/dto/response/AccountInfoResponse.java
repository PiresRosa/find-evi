package com.dnd.spaced.domain.account.presentation.dto.response;

import com.dnd.spaced.domain.account.application.dto.response.ReadAccountInfoDto;

public record AccountInfoResponse(
        String nickname,
        String profileImage,
        CareerInfoResponse careerInfo,
        QuizInfoResponse quizInfo
) {

    public static AccountInfoResponse of(ReadAccountInfoDto dto, String baseUrl, String imageUri) {
        String profileImage = baseUrl + imageUri + dto.profileImage();
        CareerInfoResponse careerInfo = new CareerInfoResponse(dto.jobGroup(), dto.company(), dto.experience());
        QuizInfoResponse quizInfo = new QuizInfoResponse(
                dto.totalCategoryQuizCount(),
                dto.designQuizCount(),
                dto.businessQuizCount(),
                dto.developQuizCount()
        );

        return new AccountInfoResponse(dto.nickname(), profileImage, careerInfo, quizInfo);
    }

    private record CareerInfoResponse(String jobGroup, String company, String experience) {
    }

    private record QuizInfoResponse(
            int totalCategoryQuizCount,
            int designQuizCount,
            int businessQuizCount,
            int developQuizCount
    ) {
    }
}
