package com.dnd.spaced.domain.word.domain.repository.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class UnsupportedWordSortConditionException extends BaseClientException {

    public UnsupportedWordSortConditionException() {
        super(ExceptionCode.UNSUPPORTED_COMMENT_SORT_CONDITION);
    }
}
