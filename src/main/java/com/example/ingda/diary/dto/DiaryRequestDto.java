package com.example.ingda.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiaryRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9@$!%*?& ]+$", message = "영어, 숫자, 특수기호만 입력 가능합니다.")
    private String subject;
    @Pattern(regexp = "^[a-zA-Z0-9@$!%*?& ]+$", message = "영어, 숫자, 특수기호만 입력 가능합니다.")
    private String content;
    private LocalDate writeDate;
}
