package com.example.ingda.domain.score.entity;


import com.example.ingda.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Long loginScore;
    private Long diaryScore;
    private Long reviewScore;

    public void addLoginScore(Long loginScore){
        this.loginScore += loginScore;
    }

    public void addDiaryScore(Long diaryScore){
        this.diaryScore += diaryScore;
    }

    public void addReviewScore(Long loginScore){
        this.reviewScore += reviewScore;
    }

    public Long getTotalScore(){
        return this.loginScore + this.diaryScore + this.reviewScore;
    }
}
