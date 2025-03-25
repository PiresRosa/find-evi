package com.dnd.spaced.domain.account.domain.exception;

import com.dnd.spaced.global.exception.BaseServerException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class InvalidNicknameException extends BaseServerException {

    public InvalidNicknameException() {
        super(ExceptionCode.INVALID_NICKNAME);
    }
}
