package com.dnd.spaced.domain.quiz.domain.repository;

import com.dnd.spaced.domain.quiz.domain.Quiz;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizCrudRepository extends CrudRepository<Quiz, Long> {
}
