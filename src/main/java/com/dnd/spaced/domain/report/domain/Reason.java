package com.dnd.spaced.domain.report.domain;

import com.dnd.spaced.domain.report.domain.exception.InvalidReasonNameException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Reason {
    PROFANITY("욕설 및 비방"),
    SPAM("스팸"),
    UNRELATED_CONTENT("해당 용어와 관련 없는 댓글"),
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
