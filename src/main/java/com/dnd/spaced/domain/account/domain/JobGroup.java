package com.dnd.spaced.domain.account.domain;

import com.dnd.spaced.domain.account.domain.exception.InvalidJobGroupException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum JobGroup {
    DEVELOP("개발"), DESIGN("디자인");

    private final String name;

    JobGroup(String name) {
        this.name = name;
    }

    public static JobGroup find(String name) {
        return Arrays.stream(JobGroup.values())
                     .filter(jobGroup -> jobGroup.name.equals(name))
                     .findAny()
                     .orElseThrow(InvalidJobGroupException::new);
    }
}
