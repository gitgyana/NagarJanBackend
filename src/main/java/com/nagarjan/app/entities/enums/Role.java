package com.nagarjan.app.entities.enums;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum Role {

    CITIZEN(1L),
    ADMIN(2L),
    OPERATOR(3L);

    private final Long code;

    Role(Long code) {
        this.code = code;
    }

    public static Role fromCode(Long code) {
        for (Role role : values()) {
            if (Objects.equals(role.code, code)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}