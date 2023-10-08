package com.example.ingda.member.controller;

import com.example.ingda.common.MessageCode;
import com.example.ingda.common.ResponseMessage;
import com.example.ingda.member.dto.MemberRequestDto;
import com.example.ingda.member.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping(value = "/email")
    public ResponseMessage<?> sendEmailForVerified(@RequestBody MemberRequestDto memberRequestDto){
        emailService.sendEmailForVerified(memberRequestDto);
        return new ResponseMessage<>(MessageCode.SUCCESS, null);
    }

    @PostMapping(value = "/email/verifying")
    public ResponseMessage<?> verifyingEmail(@RequestBody MemberRequestDto memberRequestDto){
        emailService.sendEmailForVerified(memberRequestDto);
        return null;
    }
}
