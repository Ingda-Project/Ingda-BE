package com.example.ingda.domain.diary.service;

import com.example.ingda.common.exception.CustomException;
import com.example.ingda.common.exception.ErrorCode;
import com.example.ingda.domain.diary.dto.DiaryRequestDto;
import com.example.ingda.domain.diary.dto.DiaryResponseDto;
import com.example.ingda.domain.diary.entity.Diary;
import com.example.ingda.domain.diary.mapper.DiaryMapper;
import com.example.ingda.domain.diary.repository.DiaryRepository;
import com.example.ingda.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    @Transactional
    public void writeDiary(Member member, DiaryRequestDto diaryRequestDto) {
        Optional<Diary> byWriteDate = diaryRepository.findByWriteDateAndMember(diaryRequestDto.getWriteDate(), member);
        if(byWriteDate.isPresent()) throw new CustomException(ErrorCode.DIARY_COUNT_LIMIT);

        diaryRepository.save(Diary.builder()
                                    .subject(diaryRequestDto.getSubject())
                                    .content(diaryRequestDto.getContent())
                                    .writeDate(diaryRequestDto.getWriteDate())
                                    .member(member)
                                    .review(0L)
                                    .build());
    }

    @Transactional(readOnly = true)
    public List<DiaryResponseDto> getDiariesLastOneYear(Member member) {
        List<Diary> diaries =
                diaryRepository.findByMemberAndWriteDateBetweenOrderByWriteDateDesc(member, LocalDate.now().minusYears(1L), LocalDate.now());
        return DiaryMapper.INSTANCE.diariesToResponseDtoList(diaries);

    }

    @Transactional(readOnly = true)
    public DiaryResponseDto getDiaryDetail(Member member, Long diaryId){

        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
                () -> new CustomException(ErrorCode.DIARY_NOT_FOUND)
        );

        if(!diary.getMember().getMemberId().equals(member.getMemberId())){
            throw new CustomException(ErrorCode.AUTH_FAIL);
        }

        return DiaryMapper.INSTANCE.diaryToResponseDto(diary);
    }

    @Transactional
    public void modifyDiaryContents(Member member, Long diaryId, DiaryRequestDto diaryRequestDto){
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
                () -> new CustomException(ErrorCode.DIARY_NOT_FOUND)
        );

        if(!diary.getMember().getMemberId().equals(member.getMemberId())){
            throw new CustomException(ErrorCode.AUTH_FAIL);
        }
        diary.modifyDiary(diaryRequestDto.getSubject(), diaryRequestDto.getContent());
    }

    @Transactional
    public void deleteDiary(Member member, Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
                () -> new CustomException(ErrorCode.DIARY_NOT_FOUND)
        );

        if(!diary.getMember().getMemberId().equals(member.getMemberId())){
            throw new CustomException(ErrorCode.AUTH_FAIL);
        }
        diaryRepository.delete(diary);
    }
}
