package com.example.ingda.common.type;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRoleType {
    ADMIN("admin"),
    MEMBER("member");

    private final String value;
}
