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

    /**
     * 스킬 저장 로직.
     * 기존에 스킬 정보가 있으면 업데이트하고, 없으면 새로 저장.
     * @param info 유저 정보
     * @param skillRequest 카테고리와 맞춘 문제 개수
     */
    public void addSkill(AuthAccountInfo info, SkillRequest skillRequest) {
        Skill skill = skillRepository.findByCategoryAndEmail(skillRequest.category(), info.email());
        if (skill != null) {
            skill.updateSkill(skillRequest.correctCount());
        } else {
            skill = Skill.builder()
                    .email(info.email())
                    .correctCount(skillRequest.correctCount())
                    .category(skillRequest.category())
                    .totalCount(5L)
                    .build();
        }
        // 저장된 스킬 정보 DB에 저장
        skillRepository.save(skill);
    }

    /**
     * 유저의 카테고리별 스킬 점수를 계산하고 반환.
     * @param info 유저 정보
     * @return 카테고리별 스킬 점수 응답
     */
    public Map<Category, SkillTotalScoreResponse> getSkillTotalScore(AuthAccountInfo info) {
        List<Skill> skills = skillRepository.findByEmail(info.email());
        Map<Category, SkillTotalScoreResponse> response = new HashMap<>();

        // 각 카테고리별로 점수 계산
        for (Skill skill : skills) {
            Long totalCount = calculateTotalScore(skill);  // 스킬의 정답 비율 계산
            response.put(skill.getCategory(), createSkillTotalScoreResponse(skill, totalCount));  // 결과 맵에 추가
        }

        return response;  // 최종 응답 반환
    }

    /**
     * 유저의 총점에 따라 상위 몇 퍼센트인지 계산.
     * @param info 유저 정보
     * @return 유저의 상위 퍼센트
     */
    public Long getTotalMyScore(AuthAccountInfo info) {
        List<Skill> skills = skillRepository.findByEmail(info.email());

        // 유저의 총 스킬 점수 합계 계산
        Long userTotalScore = skills.stream()
                .mapToLong(this::calculateTotalScore)  // 스킬 점수를 합산
                .sum();

        // 전체 평균 점수 계산
        Long averagePeopleScore = calculateAveragePeopleScore();

        // 상위 퍼센트 계산 (평균이 0이면 0 반환)
        return (averagePeopleScore == 0) ? 0 : (userTotalScore * 100) / averagePeopleScore;
    }

    /**
     * 개별 스킬의 정답 비율을 계산하는 메서드.
     * @param skill 스킬 정보
     * @return 정답 비율 (총점)
     */
    private Long calculateTotalScore(Skill skill) {
        return (skill.getCorrectCount() == 0) ? 0 : (skill.getTotalCount() * 100) / skill.getCorrectCount();
    }

    /**
     * 스킬 응답을 생성하는 메서드.
     * @param skill 스킬 정보
     * @param totalCount 계산된 총점
     * @return 카테고리별 스킬 응답 DTO
     */
    private SkillTotalScoreResponse createSkillTotalScoreResponse(Skill skill, Long totalCount) {
        return SkillTotalScoreResponse.builder()
                .totalScore(totalCount)           // 계산된 총점
                .currentCount(skill.getCorrectCount())  // 맞춘 문제 개수
                .totalCount(skill.getTotalCount())      // 총 문제 개수
                .build();
    }

    /**
     * 전체 유저의 평균 스킬 점수를 계산하는 메서드.
     * @return 평균 스킬 점수
     */
    private Long calculateAveragePeopleScore() {
        List<Skill> allSkills = skillRepository.findAll();  // 모든 유저의 스킬 정보 조회
        Long totalPeopleScore = allSkills.stream()
                .mapToLong(Skill::getCorrectCount)  // 전체 유저의 맞춘 문제 수 합산
                .sum();
        return (allSkills.size() == 0) ? 0 : totalPeopleScore / allSkills.size();  // 평균 점수 계산
    }
}
