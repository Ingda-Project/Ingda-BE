package com.example.ingda.domain.member.controller;

import com.example.ingda.common.MessageCode;
import com.example.ingda.common.ResponseMessage;
import com.example.ingda.domain.member.dto.MemberRequestDto;
import com.example.ingda.domain.member.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
    public ResponseMessage<?> verifyingEmail(@RequestBody Map<String, String> bodyMap){
        emailService.verifyingEmail(bodyMap);
        return new ResponseMessage<>(MessageCode.SUCCESS, null);
    }

}
