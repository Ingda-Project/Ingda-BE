package com.example.ingda.domain.score.controller;


import com.example.ingda.common.MessageCode;
import com.example.ingda.common.ResponseMessage;
import com.example.ingda.domain.score.dto.ResponseRankingDto;
import com.example.ingda.domain.score.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScoreController {
    private final ScoreService scoreService;

    @GetMapping(value = "/rankings")
    public ResponseMessage<?> getRankingTopList(){
        List<ResponseRankingDto> rankingList = scoreService.getRankingTopList();
        return new ResponseMessage<>(MessageCode.SUCCESS, rankingList);
    }

    @GetMapping(value = "/ranking")
    public ResponseMessage<?> getMyRankingInfo(){
        List<ResponseRankingDto> rankingList = scoreService.getRankingTopList();
        return new ResponseMessage<>(MessageCode.SUCCESS, rankingList);
    }
}
