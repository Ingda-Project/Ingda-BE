package com.example.ingda.domain.admin.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminMemberRequestDto {
    private String nickname;
    private Boolean status;
}
