package com.dnd.spaced.domain.account.domain;

import com.dnd.spaced.domain.account.domain.exception.InvalidCompanyException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Company {
    MAJOR("대기업"),
    MIDSIZE("중견기업"),
    SMALL("중소기업"),
    STARTUP("스타트업"),
    FOREIGN("외국계"),
    JOB_HUNTER_INTERN("취준생/인턴"),
    BLIND("비공개")
    ;

    private final String name;

    Company(String name) {
        this.name = name;
    }

    public static Company find(String name) {
        return Arrays.stream(Company.values())
                     .filter(company -> company.name.equals(name))
                     .findAny()
                     .orElseThrow(InvalidCompanyException::new);
    }
}
