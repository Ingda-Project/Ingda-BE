package com.example.ingda.member.controller;

import com.example.ingda.common.MessageCode;
import com.example.ingda.common.ResponseMessage;
import com.example.ingda.member.dto.MemberRequestDto;
import com.example.ingda.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "/member")
    public ResponseMessage<?> createMember(@RequestBody @Valid MemberRequestDto memberRequestDto){
        memberService.createMember(memberRequestDto);
        return new ResponseMessage<>(MessageCode.SUCCESS, null);
    }

}
