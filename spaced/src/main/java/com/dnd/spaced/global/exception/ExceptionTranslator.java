package com.dnd.spaced.global.exception;

import com.dnd.spaced.global.exception.dto.ExceptionDto;
import com.dnd.spaced.global.exception.dto.ParameterExceptionDto;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

@Getter
public enum ExceptionTranslator {
    METHOD_ARGUMENT_NOT_VALID_EXCEPTION(
            HttpStatus.BAD_REQUEST,
            ExceptionCode.VALIDATION_ERROR,
            "유효한 파라미터 값을 입력해주세요."
    );

    private static final String PARAMETER_SEPARATOR = ", ";

    private final HttpStatus httpStatus;
    private final ExceptionCode code;
    private final String message;

    ExceptionTranslator(HttpStatus httpStatus, ExceptionCode code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public static ExceptionTranslator find(ExceptionCode code) {
        return Arrays.stream(ExceptionTranslator.values())
                     .filter(translator -> translator.code == code)
                     .findAny()
                     .orElseThrow(() -> new IllegalStateException("정의되지 않은 예외입니다."));
    }

    public ParameterExceptionDto translate(List<FieldError> errors) {
        String parameters = errors.stream()
                                  .map(FieldError::getField)
                                  .collect(Collectors.joining(PARAMETER_SEPARATOR));

        return new ParameterExceptionDto(this.code.toString(), parameters, this.message);
    }

    public ExceptionDto translate() {
        return new ExceptionDto(this.code.toString(), this.message);
    }
}
