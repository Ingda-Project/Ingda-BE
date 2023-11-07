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

    @OneToOne(mappedBy = "score", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    private Integer loginScore;
    private Integer diaryScore;
    private Integer reviewScore;

    public void addLoginScore(Integer loginScore){
        this.loginScore += loginScore;
    }

    public void addDiaryScore(Integer diaryScore){
        this.diaryScore += diaryScore;
    }

    public void addReviewScore(Integer loginScore){
        this.reviewScore += reviewScore;
    }

    public Integer getTotalScore(){
        return this.loginScore + this.diaryScore + this.reviewScore;
    }
}
