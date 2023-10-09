package com.example.ingda.diary.controller;

import com.example.ingda.common.MessageCode;
import com.example.ingda.common.ResponseMessage;
import com.example.ingda.diary.dto.DiaryRequestDto;
import com.example.ingda.diary.service.DiaryService;
import com.example.ingda.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping(value = "/diary")
    public ResponseMessage<?> writeDiary(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody @Valid  DiaryRequestDto diaryRequestDto){
        diaryService.writeDiary(userDetails.getMember(), diaryRequestDto);
        return new ResponseMessage<>(MessageCode.SUCCESS, null);
    }
}
