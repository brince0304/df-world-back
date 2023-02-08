package com.dfoff.demo.domain.enums.useraccount;

public enum SecurityRole {
    ROLE_USER, ROLE_ADMIN;

    public String getValue() {
        return name();
    }
}
