package com.example.ingda.domain.member.service;


import com.example.ingda.common.exception.CustomException;
import com.example.ingda.common.exception.ErrorCode;
import com.example.ingda.domain.member.type.UserRoleType;
import com.example.ingda.domain.member.dto.MemberPasswordRequestDto;
import com.example.ingda.domain.member.dto.MemberRequestDto;
import com.example.ingda.domain.member.dto.MemberResponseDto;
import com.example.ingda.domain.member.entity.Member;
import com.example.ingda.domain.member.entity.TempEmail;
import com.example.ingda.domain.member.mapper.MemberMapper;
import com.example.ingda.domain.member.repository.MemberRepository;
import com.example.ingda.domain.member.repository.TempEmailRepository;
import com.example.ingda.domain.score.dto.ScoreEventData;
import com.example.ingda.domain.score.entity.Score;
import com.example.ingda.domain.score.repository.ScoreRepository;
import com.example.ingda.domain.score.type.ScoreType;
import com.example.ingda.security.UserDetailsImpl;
import com.example.ingda.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final TempEmailRepository tempEmailRepository;
    private final MemberRepository memberRepository;
    private final ScoreRepository scoreRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher publisher;
    private final JwtUtil jwtUtil;

    @Transactional
    public void createMember(MemberRequestDto memberRequestDto) {

        Optional<Member> member = memberRepository.findByEmail(memberRequestDto.getEmail());
        if(member.isPresent()) throw new CustomException(ErrorCode.EMAIL_DUPLICATED);

        TempEmail tempEmailDto = tempEmailRepository.findById(memberRequestDto.getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.VERIFYING_CODE_WRONG)
        );
        log.info("email verified >> {}", tempEmailDto.isVerified());
        if(!tempEmailDto.isVerified()){
            throw new CustomException(ErrorCode.VERIFYING_CODE_WRONG);
        }

        checkNickname(memberRequestDto.getNickname());

        String encodingPassword = passwordEncoder.encode(memberRequestDto.getPassword());

        Score score = Score.builder()
                .loginScore(0)
                .loginCount(1)
                .diaryScore(0)
                .diaryCount(1)
                .reviewScore(0)
                .build();
        scoreRepository.save(score);

        memberRepository.save(Member.builder()
                                    .email(memberRequestDto.getEmail())
                                    .nickname(memberRequestDto.getNickname())
                                    .password(encodingPassword)
                                    .userRole(UserRoleType.MEMBER)
                                    .birth(memberRequestDto.getBirth())
                                    .sex(memberRequestDto.getSex())
                                    .score(score)
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


    @Transactional
    public void login(MemberRequestDto memberRequestDto, HttpServletResponse response) {
        //ToDo => account lock system
        Member member = memberRepository.findByEmail(memberRequestDto.getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.LOGIN_FAILED)
        );

        if(member.getInactive() != null) throw new CustomException(ErrorCode.INACTIVE_MEMBER);

        if(!passwordEncoder.matches(memberRequestDto.getPassword(), member.getPassword())){
            throw new CustomException(ErrorCode.LOGIN_FAILED);
        }

        member.lastConnectedAt();

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member.getEmail()));

        if(member.getScore().getLoginCount() > 0){
            publisher.publishEvent(ScoreEventData.builder()
                    .member(member)
                    .scoreType(ScoreType.LOGIN)
                    .build());
        }
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
