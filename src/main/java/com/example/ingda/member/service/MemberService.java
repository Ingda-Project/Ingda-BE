package com.example.ingda.member.service;


import com.example.ingda.common.exception.CustomException;
import com.example.ingda.common.exception.ErrorCode;
import com.example.ingda.member.dto.MemberPasswordRequestDto;
import com.example.ingda.member.dto.MemberRequestDto;
import com.example.ingda.member.entity.Member;
import com.example.ingda.member.repository.MemberRepository;
import com.example.ingda.security.UserDetailsImpl;
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
        Member member = memberRepository.findByEmail(userDetails.getMember().getEmail());
        member.updateNickname(nickname);
    }

    @Transactional
    public void updatePassword(UserDetailsImpl userDetails, MemberPasswordRequestDto passwordRequestDto) {
        Member member = memberRepository.findByEmail(userDetails.getMember().getEmail());
        if(!passwordEncoder.matches(passwordRequestDto.getOldPassword(), member.getPassword())){
            throw new CustomException(ErrorCode.PASSWORD_INCORRECT);
        };

        String encodingPassword = passwordEncoder.encode(passwordRequestDto.getNewPassword());
        member.updatePassword(encodingPassword);
    }
}
