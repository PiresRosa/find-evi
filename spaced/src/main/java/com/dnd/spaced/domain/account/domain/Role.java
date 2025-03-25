package com.dnd.spaced.domain.account.domain;

import com.dnd.spaced.domain.account.domain.exception.InvalidRoleNameException;
import java.util.Arrays;

public enum Role {
    ROLE_ADMIN, ROLE_USER;

    public static Role findBy(String roleName) {
        return Arrays.stream(Role.values())
                .filter(role -> role.name().equalsIgnoreCase(roleName))
                .findAny()
                .orElseThrow(InvalidRoleNameException::new);
    }
}
