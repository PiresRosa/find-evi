package com.dnd.spaced.domain.skill.domain.repository;

import com.dnd.spaced.domain.skill.domain.Category;
import com.dnd.spaced.domain.skill.domain.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    boolean existsByCategoryAndEmail(Category category, String email);
    Skill findByCategoryAndEmail(Category category, String email);
    List<Skill> findByEmail(String email);

}
