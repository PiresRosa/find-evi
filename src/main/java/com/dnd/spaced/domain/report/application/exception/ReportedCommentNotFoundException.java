package com.dnd.spaced.domain.report.application.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class ReportedCommentNotFoundException extends BaseClientException {

    public ReportedCommentNotFoundException() {
        super(ExceptionCode.REPORTED_COMMENT_NOT_FOUND);
    }
}
