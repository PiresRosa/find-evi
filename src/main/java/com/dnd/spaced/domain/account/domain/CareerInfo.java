package com.dnd.spaced.domain.account.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CareerInfo {

    @Enumerated(EnumType.STRING)
    private JobGroup jobGroup;

    @Enumerated(EnumType.STRING)
    private Company company;

    @Enumerated(EnumType.STRING)
    private Experience experience;

    @Builder
    private CareerInfo(String jobGroup, String company, String experience) {
        this.jobGroup = JobGroup.find(jobGroup);
        this.company = Company.find(company);
        this.experience = Experience.find(experience);
    }
}
