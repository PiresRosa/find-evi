package com.dnd.spaced.domain.quiz.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Enumerated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String questionText;

    private String exampleText;

    @OneToMany(mappedBy = "quizQuestion", cascade = CascadeType.ALL)
    private List<QuizOption> options = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "correct_option_id")
    private QuizOption correctOption;

    @Builder
    private QuizQuestion(
            Quiz quiz,
            String categoryName,
            String questionText,
            String exampleText,
            List<QuizOption> options,
            QuizOption correctOption
    ) {
        this.quiz = quiz;
        this.category = Category.findBy(categoryName);
        this.questionText = questionText;
        this.exampleText = exampleText;
        this.options = options;
        this.correctOption = correctOption;
    }

    public boolean isCorrect(QuizOption selectedOption) {
        return selectedOption.equals(correctOption);
    }
}
