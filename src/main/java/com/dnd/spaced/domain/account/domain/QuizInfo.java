package com.dnd.spaced.domain.account.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizInfo {

    private int businessQuizTryCount = 0;
    private int developQuizTryCount = 0;
    private int designQuizTryCount = 0;
    private int totalCategoryQuizTryCount = 0;

    public void updateBusinessTryCount() {
        this.businessQuizTryCount++;
    }

    public void updateDevelopTryCount() {
        this.developQuizTryCount++;
    }

    public void updateDesignTryCount() {
        this.designQuizTryCount++;
    }

    public void updateTotalCategoryTryCount() {
        this.totalCategoryQuizTryCount++;
    }
}
