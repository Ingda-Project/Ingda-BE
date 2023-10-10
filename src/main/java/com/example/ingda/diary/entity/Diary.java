package com.example.ingda.diary.entity;

import com.example.ingda.common.audit.BaseEntity;
import com.example.ingda.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Diary extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;

    @Column
    private String subject;

    @Column
    private String content;

    @Column
    private LocalDate writeDate;

    @Column
    private Long review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    public void modifyDiary(String subject, String content){
        this.subject = subject;
        this.content = content;
    }
}
