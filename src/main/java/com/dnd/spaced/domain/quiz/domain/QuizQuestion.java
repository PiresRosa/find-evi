package com.dnd.spaced.domain.quiz.domain;


import com.dnd.spaced.domain.word.domain.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String questionText;

    @OneToMany(mappedBy = "quizQuestion", cascade = CascadeType.ALL)
    private List<QuizOption> options = new ArrayList<>();

    private Long correctOptionId;
}
