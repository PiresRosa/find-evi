package com.dnd.spaced.domain.account.presentation.dto.response;

import com.dnd.spaced.domain.account.application.dto.response.ReadAccountInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;

public record AccountInfoResponse(

        @Schema(description = "닉네임")
        String nickname,

        @Schema(description = "프로필 이미지 URL")
        String profileImage,

        @Schema(description = "경력 정보")
        CareerInfoResponse careerInfo,

        @Schema(description = "용어 테스트 정보")
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

    private record CareerInfoResponse(

            @Schema(description = "직군")
            String jobGroup,

            @Schema(description = "회사")
            String company,

            @Schema(description = "연차")
            String experience
    ) {
    }

    private record QuizInfoResponse(

            @Schema(description = "전체 실무 용어 테스트 횟수")
            int totalCategoryQuizCount,

            @Schema(description = "디자인 용어 테스트 횟수")
            int designQuizCount,

            @Schema(description = "비즈니스 용어 테스트 횟수")
            int businessQuizCount,

            @Schema(description = "개발 용어 테스트 횟수")
            int developQuizCount
    ) {
    }
}
