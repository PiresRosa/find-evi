package com.dnd.spaced.domain.account.domain.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class InvalidExperienceException extends BaseClientException {

    public InvalidExperienceException() {
        super(ExceptionCode.INVALID_EXPERIENCE);
    }
}
