package com.dnd.spaced.domain.skill.presentation;

import com.dnd.spaced.domain.skill.presentation.dto.request.SkillRequest;
import com.dnd.spaced.domain.skill.presentation.dto.response.SkillTotalResponse;
import com.dnd.spaced.global.resolver.auth.AuthAccount;
import com.dnd.spaced.global.resolver.auth.AuthAccountInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "능력치 관련 API", description = "퀴즈 결과 저장, 카테고리별 능력치 조회, 상위 퍼센트 조회")
public interface SwaggerSkillController {

    @Operation(summary = "퀴즈 결과 저장", description = "능력치 계산을 위해 퀴즈 결과(카테고리와 맞춘 문제 수)를 저장합니다.")
    @ApiResponse(responseCode = "200", description = "OK")
    ResponseEntity<Void> addSkill(
            @Parameter(hidden = true) @AuthAccount AuthAccountInfo accountInfo,
            @RequestBody SkillRequest request
    );

    @Operation(summary = "카테고리별 능력치 조회", description = "유저의 카테고리별 능력치 점수 및 전체 평균을 조회합니다. (총점, 맞춘 문제 수, 총 문제 수, 전체 평균)")
    @ApiResponse(responseCode = "200", description = "OK")
    ResponseEntity<SkillTotalResponse> getAllAbility(
            @Parameter(hidden = true) @AuthAccount AuthAccountInfo accountInfo
    );

    @Operation(summary = "상위 퍼센트 조회", description = "유저가 상위 몇 퍼센트에 해당하는지 조회합니다.")
    @ApiResponse(responseCode = "200", description = "OK")
    ResponseEntity<Long> getPrecedence(
            @Parameter(hidden = true) @AuthAccount AuthAccountInfo accountInfo
    );
}
