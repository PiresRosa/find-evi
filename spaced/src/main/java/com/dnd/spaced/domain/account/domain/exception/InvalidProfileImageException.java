package com.dnd.spaced.domain.account.domain.exception;

import com.dnd.spaced.global.exception.BaseServerException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class InvalidProfileImageException extends BaseServerException {

    public InvalidProfileImageException() {
        super(ExceptionCode.INVALID_PROFILE_IMAGE);
    }
}
