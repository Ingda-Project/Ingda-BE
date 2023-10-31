package com.example.ingda.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {

    @Email(message = "이메일 형식이 맞지 않습니다.")
    private String email;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$", message = "비밀번호 형식이 맞지 않습니다. ")
    private String password;
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]{2,20}$", message = "닉네임 형식이 맞지 않습니다.")
    private String nickname;
    private String sex;
    private LocalDate birth;

}
