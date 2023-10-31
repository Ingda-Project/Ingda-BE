package com.example.ingda.domain.diary.mapper;

import com.example.ingda.domain.diary.dto.DiaryResponseDto;
import com.example.ingda.domain.diary.entity.Diary;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DiaryMapper {
    DiaryMapper INSTANCE = Mappers.getMapper(DiaryMapper.class);

    DiaryResponseDto diaryToResponseDto(Diary diary);
    List<DiaryResponseDto> diariesToResponseDtoList(List<Diary> diaries);
}
