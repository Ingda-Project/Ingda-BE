package com.example.ingda.domain.member.controller;

import com.example.ingda.common.MessageCode;
import com.example.ingda.common.ResponseMessage;
import com.example.ingda.domain.member.dto.MemberPasswordRequestDto;
import com.example.ingda.domain.member.dto.MemberRequestDto;
import com.example.ingda.domain.member.service.KakaoService;
import com.example.ingda.domain.member.service.MemberService;
import com.example.ingda.security.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final KakaoService kakaoService;

    @PostMapping(value = "/member")
    public ResponseMessage<?> createMember(@RequestBody @Valid MemberRequestDto memberRequestDto){
        memberService.createMember(memberRequestDto);
        return new ResponseMessage<>(MessageCode.SUCCESS, null);
    }

    @PostMapping(value = "/login")
    public ResponseMessage<?> login(@RequestBody MemberRequestDto memberRequestDto
                                    , HttpServletResponse response){
        memberService.login(memberRequestDto, response);
        return new ResponseMessage<>(MessageCode.SUCCESS, null);
    }

    @GetMapping(value = "/member")
    public ResponseMessage<?> getMemberInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseMessage<>(MessageCode.SUCCESS, memberService.getMemberInfo(userDetails.getEmail()));
    }

    @GetMapping(value = "/member/nickname")
    public ResponseMessage<?> checkNickname(@RequestParam String nickname){
        memberService.checkNickname(nickname);
        return new ResponseMessage<>(MessageCode.SUCCESS, null);
    }

    @PutMapping(value = "/member/nickname")
    public ResponseMessage<?> updateNickname(@AuthenticationPrincipal UserDetailsImpl userDetails
                                            , @RequestBody MemberRequestDto memberRequestDto){
        memberService.updateNickname(userDetails, memberRequestDto.getNickname());
        return new ResponseMessage<>(MessageCode.SUCCESS, null);
    }

    @PutMapping(value = "/member/password")
    public ResponseMessage<?> updatePassword(@AuthenticationPrincipal UserDetailsImpl userDetails
                                            , @RequestBody MemberPasswordRequestDto passwordRequestDto){
        memberService.updatePassword(userDetails, passwordRequestDto);
        return new ResponseMessage<>(MessageCode.SUCCESS, null);
    }

    @PutMapping(value = "/member/activation")
    public ResponseMessage<?> changeAccountActivation(@RequestBody MemberRequestDto memberRequestDto){
        memberService.changeAccountActivation(memberRequestDto);
        return new ResponseMessage<>(MessageCode.SUCCESS, null);
    }

    @PutMapping(value = "/member/inactivation")
    public ResponseMessage<?> changeAccountInactivation(@AuthenticationPrincipal UserDetailsImpl userDetails){
        memberService.changeAccountInactivation(userDetails);
        return new ResponseMessage<>(MessageCode.SUCCESS, null);
    }

    @GetMapping(value = "/login/kakao")
    public ResponseMessage<?> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        kakaoService.login(code, response);
        return new ResponseMessage<>(MessageCode.SUCCESS, null);
    }


}
