package com.dnd.spaced.domain.account.domain.exception;

import com.dnd.spaced.global.exception.BaseServerException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class InvalidRoleNameException extends BaseServerException {

    public InvalidRoleNameException() {
        super(ExceptionCode.INVALID_ROLE);
    }
}
