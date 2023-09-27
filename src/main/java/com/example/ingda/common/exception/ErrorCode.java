package com.example.ingda.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    SERVER_ERROR("알 수 없는 서버 에러가 발생했습니다.", 500),
    FORBIDDEN_ERROR("서버 사용 권한이 없습니다.",403),
    TOKEN_ERROR("토큰이 유효하지 않습니다.",401),
    MEMBER_NOT_FOUND("알 수 없는 사용자입니다.", 404);

    private final String msg;
    private final int statusCode;
}
