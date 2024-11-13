package com.dnd.spaced.global.security.attribute.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class InvalidOAuth2TypeException extends BaseClientException {

    public InvalidOAuth2TypeException() {
        super(ExceptionCode.INVALID_OAUTH2_TYPE);
    }
}
