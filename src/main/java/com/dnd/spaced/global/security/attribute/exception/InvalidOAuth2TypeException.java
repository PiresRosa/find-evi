package com.dnd.spaced.global.security.attribute.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidOAuth2TypeException extends AuthenticationException {

    public InvalidOAuth2TypeException() {
        super("지원하지 않는 소셜 로그인 방식입니다.");
    }
}
