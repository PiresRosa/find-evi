package com.dnd.spaced.domain.account.presentation;

import com.dnd.spaced.domain.account.application.AccountService;
import com.dnd.spaced.domain.account.application.dto.response.ReadAccountInfoDto;
import com.dnd.spaced.domain.account.presentation.dto.AccountControllerMapper;
import com.dnd.spaced.domain.account.presentation.dto.request.ChangeAccountInfoRequest;
import com.dnd.spaced.domain.account.presentation.dto.response.AccountInfoResponse;
import com.dnd.spaced.global.config.properties.UrlProperties;
import com.dnd.spaced.global.controller.ResponseEntityConst;
import com.dnd.spaced.global.resolver.auth.AuthAccount;
import com.dnd.spaced.global.resolver.auth.AuthAccountInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController implements SwaggerAccountController {

    private final UrlProperties urlProperties;
    private final AccountService accountService;

    @PostMapping("/profile")
    public ResponseEntity<Void> changeProfileInfo(
            @AuthAccount AuthAccountInfo accountInfo,
            @Valid @RequestBody ChangeAccountInfoRequest request
    ) {
        accountService.changeProfileInfo(accountInfo.email(), request.nickname(), request.profileImage());

        return ResponseEntityConst.NO_CONTENT;
    }

    @GetMapping
    public ResponseEntity<AccountInfoResponse> findBy(@AuthAccount AuthAccountInfo accountInfo) {
        ReadAccountInfoDto result = accountService.findBy(accountInfo.email());

        return ResponseEntity.ok(AccountControllerMapper.of(result, urlProperties));
    }
}
