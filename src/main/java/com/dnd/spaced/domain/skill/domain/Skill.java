package com.dnd.spaced.domain.skill.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private Long correctCount;

    private Long totalCount;

    @Enumerated
    private Category category;

    @Builder
    public Skill(String email, Long correctCount, Long totalCount, Category category) {
        this.email = email;
        this.correctCount = correctCount;
        this.totalCount = totalCount;
        this.category = category;
    }

    public void updateSkill(Long correctCount){
        this.correctCount += correctCount;
        this.totalCount += 5;
    }
}
