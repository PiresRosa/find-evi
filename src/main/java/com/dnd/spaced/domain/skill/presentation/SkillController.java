package com.dnd.spaced.domain.skill.presentation;

import com.dnd.spaced.domain.skill.application.SkillService;
import com.dnd.spaced.domain.skill.presentation.dto.request.SkillRequest;
import com.dnd.spaced.domain.skill.presentation.dto.response.SkillTotalResponse;
import com.dnd.spaced.global.resolver.auth.AuthAccount;
import com.dnd.spaced.global.resolver.auth.AuthAccountInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/skill")
public class SkillController implements SwaggerSkillController {

    private final SkillService skillService;

    @PostMapping("/save")
    public ResponseEntity<Void> addSkill(
            @AuthAccount AuthAccountInfo accountInfo,
            @RequestBody SkillRequest request
    ) {
        skillService.addSkill(accountInfo, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ability")
    public ResponseEntity<SkillTotalResponse> getAllAbility(@AuthAccount AuthAccountInfo accountInfo) {
        return ResponseEntity.ok(skillService.getSkillTotalScore(accountInfo));
    }

    @GetMapping("/precedence")
    public ResponseEntity<Long> getPrecedence(@AuthAccount AuthAccountInfo info){
        return ResponseEntity.ok(skillService.getTotalMyScore(info));
    }
}
