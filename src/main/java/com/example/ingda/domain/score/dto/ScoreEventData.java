package com.example.ingda.domain.score.dto;

import com.example.ingda.domain.member.entity.Member;
import com.example.ingda.domain.score.type.ScoreType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreEventData{
    private Member member;
    private ScoreType scoreType;
}
