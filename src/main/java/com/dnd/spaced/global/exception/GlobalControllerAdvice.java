package com.dnd.spaced.global.exception;

import com.dnd.spaced.global.exception.dto.ExceptionDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    private static final String LOG_FORMAT = "%s : ";

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request
    ) {
        logger.error(String.format(LOG_FORMAT, ex.getClass().getSimpleName()), ex);

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        logger.warn(String.format(LOG_FORMAT, ex.getClass().getSimpleName()), ex);

        ExceptionTranslator translator = ExceptionTranslator.find(ExceptionCode.VALIDATION_ERROR);

        return ResponseEntity.status(translator.getHttpStatus())
                             .body(translator.translate(ex.getFieldErrors()));
    }

    @ExceptionHandler(BaseServerException.class)
    private ResponseEntity<ExceptionDto> handleBaseServerException(BaseServerException ex) {
        logger.error(String.format(LOG_FORMAT, ex.getClass().getSimpleName()), ex);

        ExceptionTranslator translator = ExceptionTranslator.find(ex.getCode());

        return ResponseEntity.status(translator.getHttpStatus())
                             .body(translator.translate());
    }

    @ExceptionHandler(BaseClientException.class)
    private ResponseEntity<ExceptionDto> handleBaseClientException(BaseClientException ex) {
        logger.warn(String.format(LOG_FORMAT, ex.getClass().getSimpleName()), ex);

        ExceptionTranslator translator = ExceptionTranslator.find(ex.getCode());

        return ResponseEntity.status(translator.getHttpStatus())
                             .body(translator.translate());
    }
}
