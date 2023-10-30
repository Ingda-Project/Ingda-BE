package com.example.ingda.admin;

import com.example.ingda.member.dto.MemberResponseDto;
import com.example.ingda.member.entity.Member;
import com.example.ingda.member.mapper.MemberMapper;
import com.example.ingda.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;

    public List<MemberResponseDto> getAllMemberInfo(){
        List<Member> memberList = memberRepository.findAll();
        return MemberMapper.INSTANCE.memberListToDtoList(memberList);
    }
}
