package com.example.ingda.domain.admin.dto;

import com.example.ingda.domain.member.type.SexType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminMemberResponseDto {
    private Long memberId;
    private String email;
    private String nickname;
    private Long totalScore;
    private SexType sex;
    private LocalDate birth;
    private int reviewCount;
    private LocalDateTime inactive;
}
