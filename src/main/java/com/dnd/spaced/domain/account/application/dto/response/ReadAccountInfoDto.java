package com.dnd.spaced.domain.account.application.dto.response;

import com.dnd.spaced.domain.account.domain.Account;
import com.dnd.spaced.domain.account.domain.CareerInfo;
import com.dnd.spaced.domain.account.domain.QuizInfo;

public record ReadAccountInfoDto(
        String nickname,
        String profileImage,
        String company,
        String jobGroup,
        String experience,
        int totalCategoryQuizCount,
        int designQuizCount,
        int businessQuizCount,
        int developQuizCount
) {

    public static ReadAccountInfoDto from(Account account) {
        CareerInfo careerInfo = account.getCareerInfo();
        QuizInfo quizInfo = account.getQuizInfo();

        return new ReadAccountInfoDto(
                account.getNickname(),
                account.getProfileImage(),
                careerInfo.getCompany().getName(),
                careerInfo.getJobGroup().getName(),
                careerInfo.getExperience().getName(),
                quizInfo.getTotalCategoryQuizTryCount(),
                quizInfo.getDesignQuizTryCount(),
                quizInfo.getBusinessQuizTryCount(),
                quizInfo.getDevelopQuizTryCount()
        );
    }
}
