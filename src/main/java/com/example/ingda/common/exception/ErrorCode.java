package com.example.ingda.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    SERVER_ERROR("알 수 없는 서버 에러가 발생했습니다.", 500),

    //회원 관련
    AUTH_FAIL("해당 작업에 대한 권한이 없습니다.", 403),
    FORBIDDEN_ERROR("서버 사용 권한이 없습니다.",403),
    TOKEN_ERROR("토큰이 유효하지 않습니다.",401),
    MEMBER_NOT_FOUND("알 수 없는 사용자입니다.", 400),
    EMAIL_DUPLICATED("이미 존재하는 이메일입니다.", 400),
    NICKNAME_DUPLICATED("이미 존재하는 닉네임입니다.", 400),
    PASSWORD_INCORRECT("비밀번호가 올바르지 않습니다.", 400),
    LOGIN_FAILED("로그인에 실패하였습니다.", 400),
    INACTIVE_MEMBER("비활성화 계정입니다.", 400),
    ACTIVATION_FAILED("계정 활성화에 실패하였습니다.", 400),
    VERIFYING_CODE_WRONG("이메일 인증에 실패하였습니다.", 400),
    SOCIAL_MEMBER("소셜 계정 회원입니다. 카카오나 구글 아이디로 로그인해주세요.", 400),

    //Diary 관련
    DIARY_COUNT_LIMIT("일기는 하루에 한 번만 쓰기가 가능합니다.", 400),
    DIARY_NOT_FOUND("일기에 대한 정보를 찾을 수 없습니다.", 404),

    //Review 관련
    REVIEW_COUNT_LIMIT("리뷰 가능 횟수가 부족합니다.", 400),
    GPT_ERROR("AI 네트워크 에러입니다." , 500);

    private final String msg;
    private final int statusCode;
}
