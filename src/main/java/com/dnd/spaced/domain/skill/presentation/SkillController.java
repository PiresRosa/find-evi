package com.dnd.spaced.domain.skill.presentation;

import com.dnd.spaced.domain.skill.application.SkillService;
import com.dnd.spaced.domain.skill.domain.Category;
import com.dnd.spaced.domain.skill.presentation.dto.request.SkillRequest;
import com.dnd.spaced.domain.skill.presentation.dto.response.SkillTotalScoreResponse;
import com.dnd.spaced.global.resolver.auth.AuthAccount;
import com.dnd.spaced.global.resolver.auth.AuthAccountInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/skill")
public class SkillController {

    private final SkillService skillService;

    /**
     * 퀴즈 결과 저장 API
     * @param accountInfo 유저 정보
     * @param request 퀴즈 결과 (카테고리와 맞춘 문제 수)
     */
    @PostMapping("/save")
    public void addSkill(
            @AuthAccount AuthAccountInfo accountInfo,
            @RequestBody SkillRequest request
    ) {
        skillService.addSkill(accountInfo, request);
    }

    /**
     * 전체 능력치 점수 반환 API (카테고리별)
     * @param accountInfo 유저 정보
     * @return 카테고리별 스킬 점수 응답 (총점, 맞춘 문제 수, 총 문제 수)
     */
    @GetMapping("/ability")
    public Map<Category, SkillTotalScoreResponse> getAllAbility(@AuthAccount AuthAccountInfo accountInfo) {
        return skillService.getSkillTotalScore(accountInfo);
    }

    /**
     * 상위 퍼센트 반환 API
     * @param info 유저 정보
     * @return 유저의 상위 퍼센트
     */
    @GetMapping("/precedence")
    public Long getPrecedence(@AuthAccount AuthAccountInfo info){
        return skillService.getTotalMyScore(info);
    }
}
