package com.example.ingda.domain.score.repository;

import com.example.ingda.domain.member.entity.Member;
import com.example.ingda.domain.score.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Member> {
    @Modifying()
    @Query("update Score s set s.diaryCount = 1, s.loginCount = 1")
    void resetScoreCounts();
}
