package com.dnd.spaced.domain.quiz.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_question_id")
    private QuizQuestion quizQuestion;

    @ManyToOne
    @JoinColumn(name = "selected_option_id")
    private QuizOption selectedOption;

    private boolean isCorrect;

    @Builder
    private QuizResult(
            QuizQuestion quizQuestion,
            QuizOption selectedOption,
            boolean isCorrect
    ) {
        this.quizQuestion = quizQuestion;
        this.selectedOption = selectedOption;
        this.isCorrect = isCorrect;
    }
}
