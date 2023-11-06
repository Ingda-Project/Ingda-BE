package com.example.ingda.domain.member.dto;

import com.example.ingda.domain.member.type.SexType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {

    private Long memberId;
    private String email;
    private String nickname;
    private SexType sex;
    private LocalDate birth;
    private int reviewCount;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime lastConnectedAt;
}
