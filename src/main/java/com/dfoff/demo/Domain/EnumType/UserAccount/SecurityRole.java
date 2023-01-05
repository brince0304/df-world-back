package com.dfoff.demo.Domain.EnumType.UserAccount;

public enum SecurityRole {
    ROLE_USER, ROLE_ADMIN;

    public String getValue() {
        return name();
    }
}
