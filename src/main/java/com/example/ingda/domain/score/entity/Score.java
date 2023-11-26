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
    @Column
    private Integer loginScore;
    @Column
    private Integer loginCount;
    @Column

    private Integer diaryScore;
    @Column

    private Integer diaryCount;
    @Column

    private Integer reviewScore;

    public void updateLoginScore(Integer loginScore){
        this.loginScore += loginScore;
        this.loginCount -= 1;
    }

    public void updateDiaryScore(Integer diaryScore){
        this.diaryScore += diaryScore;
        this.diaryCount -= 1;
    }

    public void addReviewScore(Integer reviewScore){
        this.reviewScore += reviewScore;
    }
    public Integer getTotalScore(){
        return this.loginScore + this.diaryScore + this.reviewScore;
    }
}
