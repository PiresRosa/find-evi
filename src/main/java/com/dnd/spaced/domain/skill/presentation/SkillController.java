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

    @PostMapping("/save")
    public void addSkill(
            @AuthAccount AuthAccountInfo accountInfo,
            @RequestBody SkillRequest request
    ) {
        skillService.addSkill(accountInfo, request);
    }

    @GetMapping("/ability")
    public Map<Category, SkillTotalScoreResponse> getAllAbility(@AuthAccount AuthAccountInfo accountInfo) {
        return skillService.getSkillTotalScore(accountInfo);
    }

    @GetMapping("/precedence")
    public Long getPrecedence(@AuthAccount AuthAccountInfo info){
        return skillService.getTotalMyScore(info);
    }
}
