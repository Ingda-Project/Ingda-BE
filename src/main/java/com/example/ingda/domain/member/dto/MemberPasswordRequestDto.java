package com.example.ingda.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberPasswordRequestDto {
    String oldPassword;
    String newPassword;
}
