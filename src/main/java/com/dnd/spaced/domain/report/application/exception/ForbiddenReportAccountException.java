package com.dnd.spaced.domain.report.application.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class ForbiddenReportAccountException extends BaseClientException {

    public ForbiddenReportAccountException() {
        super(ExceptionCode.FORBIDDEN_REPORT_ACCOUNT);
    }
}
