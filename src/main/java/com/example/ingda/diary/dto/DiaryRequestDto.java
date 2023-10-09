package com.example.ingda.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiaryRequestDto {

    private String subject;
    private String content;
    private LocalDate writeDate;
}
