package com.dnd.spaced.domain.account.domain.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class InvalidJobGroupException extends BaseClientException {

    public InvalidJobGroupException() {
        super(ExceptionCode.INVALID_JOB_GROUP);
    }
}
