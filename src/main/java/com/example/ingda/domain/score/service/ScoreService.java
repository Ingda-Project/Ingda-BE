package com.example.ingda.domain.score.service;

import com.example.ingda.domain.member.entity.Member;
import com.example.ingda.domain.score.dto.ResponseRankingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScoreService {
    private static final String RANKING = "ranking";

    private final RedisTemplate redisTemplate;

    @Transactional
    public void updateRanking(Member member){
        Integer totalScore = member.getScore().getTotalScore();
        redisTemplate.opsForZSet().add(RANKING, member.getNickname(), totalScore);
    }

    @Transactional
    public List<ResponseRankingDto> getRankingTopList(){
        List<ResponseRankingDto> responseRankingDtoList = new ArrayList<>();
        Set<ZSetOperations.TypedTuple<String>> tuples = redisTemplate.opsForZSet().reverseRangeWithScores(RANKING, 0, 10);

        Long tempScore = 0L;
        Long ranking = 0L;
        for (ZSetOperations.TypedTuple<String> tuple :tuples) {
            if(!tempScore.equals(tuple.getScore().longValue())){
                ranking++;
                tempScore = tuple.getScore().longValue();
            }

            responseRankingDtoList.add(ResponseRankingDto.builder()
                                                            .ranking(ranking)
                                                            .score(tuple.getScore().longValue())
                                                            .nickname(tuple.getValue())
                                                            .build()
            );
        }

        return responseRankingDtoList;
    }

    @Transactional
    public List<ResponseRankingDto> getMyRankingInfo(Member member){

        List<ResponseRankingDto> responseRankingDtoList = new ArrayList<>();
        Double score = redisTemplate.opsForZSet().score(RANKING, member.getNickname());
        Long rank = redisTemplate.opsForZSet().rank(RANKING, member.getNickname());

        responseRankingDtoList.add(ResponseRankingDto.builder()
                                        .ranking(rank)
                                        .score(score.longValue())
                                        .nickname(member.getNickname())
                                        .build()
        );
        return responseRankingDtoList;
    }
}
