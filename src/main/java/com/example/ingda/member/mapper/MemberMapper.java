package com.example.ingda.member.mapper;

import com.example.ingda.member.dto.MemberResponseDto;
import com.example.ingda.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    MemberResponseDto memberToResponseDto(Member member);
}
