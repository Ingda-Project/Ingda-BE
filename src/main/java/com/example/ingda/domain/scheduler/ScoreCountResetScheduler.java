package com.example.ingda.domain.scheduler;

import com.example.ingda.domain.score.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScoreCountResetScheduler {

    private final ScoreRepository scoreRepository;

    @Transactional
    @Scheduled(cron = "0 0 3 * * ?")
    public void scoreCountReset(){
        log.info(">>> score count reset start <<<");
        scoreRepository.resetScoreCounts();
    }
}
