package com.dnd.spaced.domain.quiz.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private QuizQuestion quizQuestion;

    @ManyToOne
    private QuizOption selectedOption;

    private boolean isCorrect;
}
