package com.dnd.spaced.domain.word.application.exception;

import com.dnd.spaced.global.exception.BaseServerException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class PopularWordScheduleNotFoundException extends BaseServerException {

    public PopularWordScheduleNotFoundException() {
        super(ExceptionCode.POPULAR_WORD_SCHEDULE_NOT_FOUND);
    }
}
