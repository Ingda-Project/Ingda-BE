package com.example.ingda.domain.score.repository;

import com.example.ingda.domain.member.entity.Member;
import com.example.ingda.domain.score.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Member> {

}
