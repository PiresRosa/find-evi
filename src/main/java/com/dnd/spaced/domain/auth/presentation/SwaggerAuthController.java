package com.dnd.spaced.domain.auth.presentation;

import com.dnd.spaced.domain.auth.presentation.dto.request.CareerInfoRequest;
import com.dnd.spaced.domain.auth.presentation.dto.response.TokenResponse;
import com.dnd.spaced.global.docs.annotation.ExceptionSpec;
import com.dnd.spaced.global.docs.annotation.ExcludeCommonHeaderSpec;
import com.dnd.spaced.global.exception.ExceptionCode;
import com.dnd.spaced.global.resolver.auth.AuthAccount;
import com.dnd.spaced.global.resolver.auth.AuthAccountInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "인증/인가 관련 API")
public interface SwaggerAuthController {

    @Operation(summary = "회원 프로필 정보 입력", description = "회원 프로필 정보 입력 API")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ExceptionSpec(values = {ExceptionCode.FORBIDDEN_ACCOUNT})
    ResponseEntity<Void> saveCareerInfo(
            @Parameter(hidden = true) @AuthAccount AuthAccountInfo accountInfo,
            @Valid @RequestBody CareerInfoRequest request
    );

    @ExcludeCommonHeaderSpec
    @Operation(summary = "토큰 재발급", description = "토큰 재발급 API")
    @Parameter(name = "refreshToken", in = ParameterIn.COOKIE, required = true, description = "액세스 토큰을 발급받기 위해 필요한 리프레시 토큰 쿠키")
    @ApiResponse(responseCode = "200", description = "OK")
    @ExceptionSpec(values = {ExceptionCode.REFRESH_TOKEN_NOT_FOUND, ExceptionCode.INVALID_TOKEN})
    ResponseEntity<TokenResponse> refreshToken(@Parameter(hidden = true) HttpServletRequest request);

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 API")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ExceptionSpec(values = {ExceptionCode.FORBIDDEN_ACCOUNT})
    ResponseEntity<Void> withdrawal(@AuthAccount AuthAccountInfo accountInfo);
}
