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
    ),
    INVALID_TOKEN_EXCEPTION(
            HttpStatus.UNAUTHORIZED,
            ExceptionCode.INVALID_TOKEN,
            "유효하지 않은 토큰입니다."
    ),
    EXPIRED_TOKEN_EXCEPTION(
            HttpStatus.UNAUTHORIZED,
            ExceptionCode.EXPIRED_TOKEN,
            "만료된 토큰입니다."
    ),
    FORBIDDEN_ACCOUNT_EXCEPTION(
            HttpStatus.FORBIDDEN,
            ExceptionCode.FORBIDDEN_ACCOUNT,
            "권한이 없습니다."
    ),
    INVALID_JOB_GROUP_EXCEPTION(
            HttpStatus.BAD_REQUEST,
            ExceptionCode.INVALID_JOB_GROUP,
            "직군은 개발, 디자인만 선택 가능합니다."
    ),
    INVALID_EMAIL_EXCEPTION(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ExceptionCode.INVALID_EMAIL,
            "유효한 이메일을 작성해주세요."
    ),
    INVALID_NICKNAME_EXCEPTION(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ExceptionCode.INVALID_NICKNAME,
            "유효한 닉네임을 작성해주세요."
    ),
    INVALID_PROFILE_IMAGE_EXCEPTION(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ExceptionCode.INVALID_PROFILE_IMAGE,
            "유효한 프로필 이미지를 지정해주세요."
    ),
    INVALID_ROLE_EXCEPTION(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ExceptionCode.INVALID_ROLE,
            "유효한 권한을 설정해주세요."
    ),
    FORBIDDEN_BOOKMARK_EXCEPTION(
            HttpStatus.FORBIDDEN,
            ExceptionCode.FORBIDDEN_BOOKMARK,
            "권한이 없습니다."
    ),
    WORD_NOT_FOUND_EXCEPTION(
            HttpStatus.NOT_FOUND,
            ExceptionCode.WORD_NOT_FOUND,
            "지정한 용어를 찾을 수 없습니다."
    ),
    INVALID_CATEGORY_EXCEPTION(
            HttpStatus.BAD_REQUEST,
            ExceptionCode.INVALID_CATEGORY,
            "카테고리는 개발, 디자인, 비즈니스만 입력할 수 있습니다."
    ),
    INVALID_EXCEPTION_EXCEPTION(
            HttpStatus.BAD_REQUEST,
            ExceptionCode.INVALID_EXAMPLE,
            "예문은 최소 1글자, 최대 50글자여야 합니다."
    ),
    INVALID_MEANING_EXCEPTION(
            HttpStatus.BAD_REQUEST,
            ExceptionCode.INVALID_MEANING,
            "용어 뜻은 최소 10글자, 최대 70글자여야 합니다."
    ),
    INVALID_NAME_EXCEPTION(
            HttpStatus.BAD_REQUEST,
            ExceptionCode.INVALID_NAME,
            "용어 이름을 입력해주세요."
    ),
    INVALID_PRONUNCIATION_EXCEPTION(
            HttpStatus.BAD_REQUEST,
            ExceptionCode.INVALID_PRONUNCIATION,
            "용어 발음을 입력해주세요."
    ),
    UNSUPPORTED_COMMENT_SORT_CONDITION_EXCEPTION(
            HttpStatus.BAD_REQUEST,
            ExceptionCode.UNSUPPORTED_COMMENT_SORT_CONDITION,
            "지원하지 않는 정렬 방식입니다."
    ),
    COMMENT_NOT_FOUND_EXCEPTION(
            HttpStatus.NOT_FOUND,
            ExceptionCode.COMMENT_NOT_FOUND,
            "댓글을 찾을 수 없습니다."
    ),
    COMMENT_WORD_NOT_FOUND_EXCEPTION(
            HttpStatus.NOT_FOUND,
            ExceptionCode.COMMENT_WORD_NOT_FOUND,
            "댓글이 등록된 용어를 찾을 수 없습니다."
    ),
    FORBIDDEN_DELETE_COMMENT_EXCEPTION(
            HttpStatus.FORBIDDEN,
            ExceptionCode.FORBIDDEN_DELETE_COMMENT,
            "댓글을 삭제할 수 있는 권한이 없습니다."
    ),
    FORBIDDEN_LIKE_EXCEPTION(
            HttpStatus.FORBIDDEN,
            ExceptionCode.FORBIDDEN_LIKE,
            "권한이 없습니다."
    ),
    FORBIDDEN_UPDATE_COMMENT_EXCEPTION(
            HttpStatus.FORBIDDEN,
            ExceptionCode.FORBIDDEN_UPDATE_COMMENT,
            "댓글을 수정할 수 있는 권한이 없습니다."
    ),
    UNAUTHORIZED_COMMENT_EXCEPTION(
            HttpStatus.UNAUTHORIZED,
            ExceptionCode.UNAUTHORIZED_COMMENT,
            "로그인이 필요한 기능입니다."
    ),
    INVALID_CONTENT_EXCEPTION(
            HttpStatus.BAD_REQUEST,
            ExceptionCode.INVALID_CONTENT,
            "댓글은 최소 1글자, 최대 100글자여야 합니다."
    )
    ;

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
