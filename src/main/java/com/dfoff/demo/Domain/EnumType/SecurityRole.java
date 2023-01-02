package com.dfoff.demo.Domain.EnumType;

public enum SecurityRole {
    ROLE_USER, ROLE_ADMIN;

    public String getValue() {
        return name();
    }
}
