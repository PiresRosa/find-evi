package com.dnd.spaced.domain.admin.application.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class ReportNotFoundException extends BaseClientException {

    public ReportNotFoundException() {super(ExceptionCode.REPORT_NOT_FOUND);}
}
