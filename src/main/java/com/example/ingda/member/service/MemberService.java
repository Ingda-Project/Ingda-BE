package com.example.ingda.member.service;


import com.example.ingda.common.exception.CustomException;
import com.example.ingda.common.exception.ErrorCode;
import com.example.ingda.member.dto.MemberRequestDto;
import com.example.ingda.member.entity.Member;
import com.example.ingda.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public void createMember(MemberRequestDto memberRequestDto) {
        Member member = memberRepository.findByEmail(memberRequestDto.getEmail());
        if(member != null) throw new CustomException(ErrorCode.EMAIL_DUPLICATED);

        member = memberRepository.findByNickname(memberRequestDto.getNickname());
        if(member != null) throw new CustomException(ErrorCode.NICKNAME_DUPLICATED);

        String encodingPassword = passwordEncoder.encode(memberRequestDto.getPassword());
        member = Member.builder()
                .email(memberRequestDto.getEmail())
                .nickname(memberRequestDto.getNickname())
                .password(encodingPassword)
                .build();
        memberRepository.save(member);

    }
}
