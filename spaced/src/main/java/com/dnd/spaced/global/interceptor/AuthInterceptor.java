package com.dnd.spaced.global.interceptor;

import com.dnd.spaced.domain.account.application.ValidateAccountService;
import com.dnd.spaced.domain.auth.application.exception.ForbiddenAccountException;
import com.dnd.spaced.domain.auth.domain.TokenDecoder;
import com.dnd.spaced.global.resolver.AuthAccountInfo;
import com.dnd.spaced.global.resolver.AuthStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthStore store;
    private final TokenDecoder tokenDecoder;
    private final ValidateAccountService validateAccountService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            store.set(new AuthAccountInfo(null));
            return true;
        }

        String email = ((UserDetails) authentication.getPrincipal()).getUsername();

        if (validateAccountService.isValidAccount(email)) {
            store.set(new AuthAccountInfo(email));
            return true;
        }

        throw new ForbiddenAccountException();
    }

    @Override
    public void afterCompletion(
            HttpServletRequest ignoreRequest,
            HttpServletResponse ignoreResponse,
            Object ignoreHandler,
            Exception ignoreEx
    ) {
        store.remove();
    }
}
