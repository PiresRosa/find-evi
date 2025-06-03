package com.dnd.spaced.domain.auth.presentation.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class RefreshTokenNotFoundException extends BaseClientException {

    public RefreshTokenNotFoundException() {
        super(ExceptionCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
