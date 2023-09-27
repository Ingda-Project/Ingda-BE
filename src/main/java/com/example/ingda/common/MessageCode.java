package com.example.ingda.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageCode {

    SUCCESS("성공", 200);

    private final String msg;
    private final int statusCode;
}
