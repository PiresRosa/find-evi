package com.dnd.spaced.domain.report.domain;

import com.dnd.spaced.domain.report.domain.exception.InvalidReasonNameException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Reason {
    SPAM("광고 및 홍보성 내용"),
    PERSONAL_INFORMATION_EXPOSURE("개인정보 노출 위험"),
    OVER_COMMENT("댓글 도배"),
    PROFANITY("욕설, 음란 등 부적절한 내용"),
    ETC("기타");

    private final String name;

    Reason(String name) {
        this.name = name;
    }

    public static Reason find(String name) {
        return Arrays.stream(Reason.values())
                     .filter(reason -> reason.name.equals(name))
                     .findAny()
                     .orElseThrow(InvalidReasonNameException::new);
    }
}
