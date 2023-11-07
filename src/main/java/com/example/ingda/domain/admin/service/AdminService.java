package com.example.ingda.domain.admin.service;

import com.example.ingda.common.exception.CustomException;
import com.example.ingda.common.exception.ErrorCode;
import com.example.ingda.domain.admin.mapper.AdminMapper;
import com.example.ingda.domain.admin.dto.AdminMemberRequestDto;
import com.example.ingda.domain.admin.dto.AdminMemberResponseDto;
import com.example.ingda.domain.member.dto.MemberRequestDto;
import com.example.ingda.domain.member.entity.Member;
import com.example.ingda.domain.member.repository.MemberRepository;
import com.example.ingda.domain.member.type.UserRoleType;
import com.example.ingda.domain.score.entity.Score;
import com.example.ingda.domain.score.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ScoreRepository scoreRepository;

    @Transactional(readOnly = true)
    public List<AdminMemberResponseDto> getAllMemberInfo(){
        //ToDo 조회 조건에 의해 검색
        List<Member> memberList = memberRepository.findAll();
        return AdminMapper.INSTANCE.memberListToResponseDtoList(memberList);
    }

    @Transactional
    public void createMember(MemberRequestDto memberRequestDto) {
        Optional<Member> member = memberRepository.findByEmail(memberRequestDto.getEmail());
        if(member.isPresent()) throw new CustomException(ErrorCode.EMAIL_DUPLICATED);

        String encodingPassword = passwordEncoder.encode(memberRequestDto.getPassword());

        Score score = Score.builder()
                .loginScore(0)
                .diaryScore(0)
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

    @Transactional
    public void deleteMember(Long memberId) {
       memberRepository.deleteById(memberId);
    }

    @Transactional
    public void updateMemberByAdmin(Long memberId, AdminMemberRequestDto adminMemberRequestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow();

        member.updateNickname(adminMemberRequestDto.getNickname());

        if(adminMemberRequestDto.getStatus()) member.changeAccountActivation();
        else member.changeAccountInactivation();

    }
}
