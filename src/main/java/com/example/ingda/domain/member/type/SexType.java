package com.example.ingda.domain.member.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SexType {
    WOMAN("woman"),
    MAN("man");

    private final String value;
}
