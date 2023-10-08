package com.example.ingda.member.service;


import com.example.ingda.common.exception.CustomException;
import com.example.ingda.common.exception.ErrorCode;
import com.example.ingda.member.dto.MemberPasswordRequestDto;
import com.example.ingda.member.dto.MemberRequestDto;
import com.example.ingda.member.dto.MemberResponseDto;
import com.example.ingda.member.entity.Member;
import com.example.ingda.member.mapper.MemberMapper;
import com.example.ingda.member.repository.MemberRepository;
import com.example.ingda.security.UserDetailsImpl;
import com.example.ingda.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void createMember(MemberRequestDto memberRequestDto) {

        Optional<Member> member = memberRepository.findByEmail(memberRequestDto.getEmail());
        if(member.isPresent()) throw new CustomException(ErrorCode.EMAIL_DUPLICATED);

        checkNickname(memberRequestDto.getNickname());

        String encodingPassword = passwordEncoder.encode(memberRequestDto.getPassword());

        memberRepository.save(Member.builder()
                                    .email(memberRequestDto.getEmail())
                                    .nickname(memberRequestDto.getNickname())
                                    .password(encodingPassword)
                                    .build());
    }

    @Transactional(readOnly = true)
    public void checkNickname(String nickname){

        Member member = memberRepository.findByNickname(nickname);
        if(member != null) throw new CustomException(ErrorCode.NICKNAME_DUPLICATED);
    }

    @Transactional
    public void updateNickname(UserDetailsImpl userDetails, String nickname) {
        Member member = memberRepository.findByEmail(userDetails.getMember().getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
        );
        member.updateNickname(nickname);
    }

    @Transactional
    public void updatePassword(UserDetailsImpl userDetails, MemberPasswordRequestDto passwordRequestDto) {
        Member member = memberRepository.findByEmail(userDetails.getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
        );
        if(!passwordEncoder.matches(passwordRequestDto.getOldPassword(), member.getPassword())){
            throw new CustomException(ErrorCode.PASSWORD_INCORRECT);
        };

        String encodingPassword = passwordEncoder.encode(passwordRequestDto.getNewPassword());
        member.updatePassword(encodingPassword);
    }

    public void login(MemberRequestDto memberRequestDto, HttpServletResponse response) {
        //ToDo => account lock system
        Member member = memberRepository.findByEmail(memberRequestDto.getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.LOGIN_FAILED)
        );

        if(member.getActive() != null) throw new CustomException(ErrorCode.INACTIVE_MEMBER);

        if(!passwordEncoder.matches(memberRequestDto.getPassword(), member.getPassword())){
            throw new CustomException(ErrorCode.LOGIN_FAILED);
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member.getEmail()));
    }

    @Transactional
    public void changeAccountActivation(MemberRequestDto memberRequestDto) {

        Member member = memberRepository.findByEmail(memberRequestDto.getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
        );
        if(!passwordEncoder.matches(memberRequestDto.getPassword(), member.getPassword())){
            throw new CustomException(ErrorCode.ACTIVATION_FAILED);
        }

        member.changeAccountActivation();

    }

    @Transactional
    public void changeAccountInactivation(UserDetailsImpl userDetails) {
        Member member = memberRepository.findByEmail(userDetails.getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
        );;
        member.changeAccountInactivation();
    }

    @Transactional(readOnly = true)
    public MemberResponseDto getMemberInfo(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
        );;
        return MemberMapper.INSTANCE.memberToResponseDto(member);
    }

}
