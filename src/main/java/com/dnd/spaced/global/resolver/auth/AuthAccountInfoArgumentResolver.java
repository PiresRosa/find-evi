package com.dnd.spaced.global.resolver.auth;

import com.dnd.spaced.global.resolver.auth.exception.AccountUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthAccountInfoArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthStore store;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthAccount.class) && parameter.getParameterType()
                                                                               .equals(AuthAccountInfo.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        AuthAccountInfo accountInfo = store.get();

        if (!isRequired(parameter) && isInvalidAccountPrincipal(accountInfo)) {
            return new AuthAccountInfo(null);
        }

        if (isInvalidAccountPrincipal(accountInfo)) {
            throw new AccountUnauthorizedException();
        }

        return accountInfo;
    }

    private boolean isRequired(MethodParameter parameter) {
        return parameter.getParameterAnnotation(AuthAccount.class)
                        .required();
    }

    private boolean isInvalidAccountPrincipal(AuthAccountInfo accountInfo) {
        return accountInfo == null || accountInfo.email() == null;
    }
}
