package com.dnd.spaced.domain.account.presentation;

import com.dnd.spaced.domain.account.presentation.dto.request.ChangeAccountInfoRequest;
import com.dnd.spaced.domain.account.presentation.dto.response.AccountInfoResponse;
import com.dnd.spaced.global.docs.annotation.ExceptionSpec;
import com.dnd.spaced.global.exception.ExceptionCode;
import com.dnd.spaced.global.resolver.auth.AuthAccount;
import com.dnd.spaced.global.resolver.auth.AuthAccountInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "회원 관련 API", description = "내 정보 수정, 내 정보 조회")
public interface SwaggerAccountController {

    @Operation(summary = "내 정보 수정", description = "내 정보 수정 API")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ExceptionSpec(values = {
            ExceptionCode.ACCOUNT_UNAUTHORIZED,
            ExceptionCode.INVALID_NICKNAME,
            ExceptionCode.INVALID_PROFILE_IMAGE
    })
    ResponseEntity<Void> changeProfileInfo(
            @Parameter(hidden = true) @AuthAccount AuthAccountInfo accountInfo,
            @Valid @RequestBody ChangeAccountInfoRequest request
    );

    @Operation(summary = "내 정보 조회", description = "내 정보 조회 API")
    @ApiResponse(responseCode = "200", description = "OK")
    @ExceptionSpec(values = {ExceptionCode.ACCOUNT_UNAUTHORIZED})
    ResponseEntity<AccountInfoResponse> findBy(@Parameter(hidden = true) @AuthAccount AuthAccountInfo accountInfo);
}
