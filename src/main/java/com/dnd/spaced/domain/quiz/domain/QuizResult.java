package com.dnd.spaced.domain.quiz.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;

    @ManyToOne
    @JoinColumn(name = "quiz_question_id")
    private QuizQuestion quizQuestion;

    @ManyToOne
    @JoinColumn(name = "selected_option_id")
    private QuizOption selectedOption;

    private boolean isCorrect;

    private LocalDate createdAt;

    @Builder
    private QuizResult(
            QuizQuestion quizQuestion,
            QuizOption selectedOption,
            boolean isCorrect,
            Long accountId,
            LocalDate createdAt
    ) {
        this.quizQuestion = quizQuestion;
        this.selectedOption = selectedOption;
        this.isCorrect = isCorrect;
        this.accountId = accountId;
        this.createdAt = createdAt;
    }
}
