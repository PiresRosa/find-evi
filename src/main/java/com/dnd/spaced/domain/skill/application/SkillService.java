package com.dnd.spaced.domain.skill.application;

import com.dnd.spaced.domain.skill.domain.Category;
import com.dnd.spaced.domain.skill.domain.Skill;
import com.dnd.spaced.domain.skill.domain.repository.SkillRepository;
import com.dnd.spaced.domain.skill.presentation.dto.request.SkillRequest;
import com.dnd.spaced.domain.skill.presentation.dto.response.SkillTotalScoreResponse;
import com.dnd.spaced.global.resolver.auth.AuthAccountInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;

    public void addSkill(AuthAccountInfo info, SkillRequest skillRequest) {
        if (skillRepository.existsByCategoryAndEmail(skillRequest.category(), info.email())) {
            // 이미 해당 카테고리의 스킬 정보가 있는 경우 업데이트
            Skill skill = skillRepository.findByCategoryAndEmail(skillRequest.category(), info.email());
            skill.addCorrectCount(skillRequest.correctCount());
            skillRepository.save(skill);

        } else {
            // 새로운 스킬 정보 저장
            skillRepository.save(Skill.builder()
                    .email(info.email())
                    .correctCount(skillRequest.correctCount())
                    .category(skillRequest.category())
                    .totalCount(5L) // 기본 총 문제 수
                    .build());
        }
    }

    public Map<Category, SkillTotalScoreResponse> getSkillTotalScore(AuthAccountInfo info){
        List<Skill> skill = skillRepository.findByEmail(info.email());
        Map<Category, SkillTotalScoreResponse> response = new HashMap<>();

        Long totalScoreResult = 0L;
        Long score = 0L;

        // 카테고리별 스킬 점수 계산
        for (Skill sl : skill) {
            Long totalCount = sl.getTotalCount() / sl.getCorrectCount() * 100;
            score+=sl.getCorrectCount();
            response.put(sl.getCategory(), SkillTotalScoreResponse.builder()
                    .totalScore(totalCount)
                    .currentCount(sl.getCorrectCount())
                    .totalCount(sl.getTotalCount())
                    .build());

            totalScoreResult += totalCount;

        }

        Long peopleTotalCount = 0L;

        List<Skill> peoplies = skillRepository.findAll();

        for (Skill sl : peoplies){
            peopleTotalCount+=sl.getCorrectCount();
        }

        Long res = peopleTotalCount/peoplies.size()/score; // 전체 인원 정답수/전체 인원/내가 맞춘 답

        Long result =totalScoreResult/3; // 전체 %

        return response;
    }

    public Long getTotalMyScore(AuthAccountInfo info) {
        List<Skill> skill = skillRepository.findByEmail(info.email());
        Map<Category, SkillTotalScoreResponse> response = new HashMap<>();

        Long totalScoreResult = 0L;
        Long score = 0L;

        // 자신의 스킬 정보를 바탕으로 총점 계산
        for (Skill sl : skill) {
            Long totalCount = sl.getCorrectCount() == 0 ? 0 : sl.getTotalCount() / sl.getCorrectCount() * 100;
            score += sl.getCorrectCount();
            response.put(sl.getCategory(), SkillTotalScoreResponse.builder()
                    .totalScore(totalCount)
                    .currentCount(sl.getCorrectCount())
                    .totalCount(sl.getTotalCount())
                    .build());

            totalScoreResult += totalCount;
        }

        // 전체 유저의 총점 계산
        List<Skill> allSkills = skillRepository.findAll();
        Long totalPeopleScore = 0L;
        for (Skill sl : allSkills) {
            totalPeopleScore += sl.getCorrectCount();
        }

        // 평균을 통해 전체 유저의 평균 점수 계산
        Long averagePeopleScore = totalPeopleScore / allSkills.size();

        // 자신의 점수가 상위 몇 퍼센트인지 계산
        Long topPercent = (score * 100) / averagePeopleScore;

        return topPercent;
    }
}
