package com.example.ingda.diary.controller;

import com.example.ingda.common.MessageCode;
import com.example.ingda.common.ResponseMessage;
import com.example.ingda.diary.dto.DiaryRequestDto;
import com.example.ingda.diary.dto.DiaryResponseDto;
import com.example.ingda.diary.service.DiaryService;
import com.example.ingda.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping(value = "/diary")
    public ResponseMessage<?> writeDiary(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody @Valid  DiaryRequestDto diaryRequestDto){
        diaryService.writeDiary(userDetails.getMember(), diaryRequestDto);
        return new ResponseMessage<>(MessageCode.SUCCESS, null);
    }

    @GetMapping("/diaries")
    public ResponseMessage<List<DiaryResponseDto>> getDiariesLastOneYear(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseMessage<>(MessageCode.SUCCESS, diaryService.getDiariesLastOneYear(userDetails.getMember()));
    }

    @GetMapping("/diaries/{diaryId}")
    public ResponseMessage<DiaryResponseDto> getDiaryDetail(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                @PathVariable(name="diaryId") Long diaryId){
        return new ResponseMessage<>(MessageCode.SUCCESS, diaryService.getDiaryDetail(userDetails.getMember(), diaryId));
    }

    @PutMapping("/diaries/{diaryId}")
    public ResponseMessage<DiaryResponseDto> modifyDiaryContents(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                            @PathVariable(name="diaryId") Long diaryId,
                                                                 @RequestBody DiaryRequestDto diaryRequestDto){
        diaryService.modifyDiaryContents(userDetails.getMember(), diaryId, diaryRequestDto);
        return new ResponseMessage<>(MessageCode.SUCCESS, null );
    }
}
