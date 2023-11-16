package com.example.ingda.domain.score.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseRankingDto {
    private Long ranking;
    private Long score;
    private String nickname;
}
