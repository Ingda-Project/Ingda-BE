package com.example.ingda.domain.score.service;

import com.example.ingda.domain.member.entity.Member;
import com.example.ingda.domain.score.dto.ScoreEventData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScoreListener {

    @Value("${score.login}")
    Integer loginScore;

    @Value("${score.diary.write}")
    Integer diaryScore;

    @Value("${score.diary.write.bonus}")
    Integer diaryBonusScore;

    @Value("${score.diary.review}")
    Integer reviewScore;

    @Async
    @EventListener
    public void addScore(ScoreEventData event){
        Member member = event.getMember();

        switch(event.getScoreType()){
            case LOGIN:
                member.getScore().addLoginScore(loginScore);
                break;
            case WRITE:
                member.getScore().addDiaryScore(diaryScore);
                break;
            case REVIEW:
                member.getScore().addReviewScore(reviewScore);

        }
    }
}
