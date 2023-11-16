package com.example.ingda.domain.score.service;

import com.example.ingda.common.audit.BaseEntity;
import com.example.ingda.domain.diary.entity.Diary;
import com.example.ingda.domain.diary.repository.DiaryRepository;
import com.example.ingda.domain.member.entity.Member;
import com.example.ingda.domain.score.dto.ScoreEventData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScoreListener {

    private final DiaryRepository diaryRepository;
    private final ScoreService scoreService;

    @Value("${score.login}")
    Integer loginScore;

    @Value("${score.diary.write}")
    Integer diaryScore;

    @Value("${score.diary.write.bonus}")
    Integer diaryBonusScore;

    @Value("${score.diary.write.continuous.days}")
    Integer bonusContinuousDays;

    @Value("${score.diary.review}")
    Integer reviewScore;

    @Async
    @Transactional
    @EventListener
    public void addScore(ScoreEventData event){
        Member member = event.getMember();

        switch(event.getScoreType()){
            case LOGIN:
                member.getScore().updateLoginScore(loginScore);
                break;
            case WRITE:
                int bonusScore = 0;
                LocalDateTime now = LocalDateTime.now();
                List<Diary> diaries = diaryRepository.findAllByMemberAndCreatedAtBetweenOrderByCreatedAt(member, now.minusDays(5), now);
                Set<LocalDate> localDates = diaries.stream().collect(Collectors.groupingBy(diary ->
                                                                        diary.getCreatedAt().toLocalDate())).keySet();

                for (int i = 0; i < bonusContinuousDays; i++) {
                    if(!localDates.contains(now.toLocalDate().minusDays(i+1))) break;
                    bonusScore += diaryBonusScore;
                }
                member.getScore().updateDiaryScore(diaryScore + bonusScore);
                break;
            case REVIEW:
                member.getScore().addReviewScore(reviewScore);

        }
        scoreService.updateRanking(member);
    }
}
