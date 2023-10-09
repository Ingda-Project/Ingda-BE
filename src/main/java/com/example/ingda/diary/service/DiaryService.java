package com.example.ingda.diary.service;

import com.example.ingda.common.exception.CustomException;
import com.example.ingda.common.exception.ErrorCode;
import com.example.ingda.diary.dto.DiaryRequestDto;
import com.example.ingda.diary.entity.Diary;
import com.example.ingda.diary.repository.DiaryRepository;
import com.example.ingda.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public void writeDiary(Member member, DiaryRequestDto diaryRequestDto) {
        Optional<Diary> byWriteDate = diaryRepository.findByWriteDate(diaryRequestDto.getWriteDate());
        if(byWriteDate.isPresent()) throw new CustomException(ErrorCode.DIARY_COUNT_LIMIT);

        diaryRepository.save(Diary.builder()
                                    .subject(diaryRequestDto.getSubject())
                                    .content(diaryRequestDto.getContent())
                                    .writeDate(diaryRequestDto.getWriteDate())
                                    .member(member)
                                    .review(0L)
                                    .build());
    }
}
