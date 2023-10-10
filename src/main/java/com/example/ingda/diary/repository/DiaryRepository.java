package com.example.ingda.diary.repository;

import com.example.ingda.diary.entity.Diary;
import com.example.ingda.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Optional<Diary> findByWriteDateAndMember(LocalDate writeDate, Member member);
    List<Diary> findByMemberAndWriteDateBetweenOrderByWriteDateDesc(Member member, LocalDate startDate, LocalDate today);
}
