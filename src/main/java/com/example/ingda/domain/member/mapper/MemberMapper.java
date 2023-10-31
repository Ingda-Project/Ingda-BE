package com.example.ingda.domain.member.mapper;

import com.example.ingda.domain.member.dto.MemberResponseDto;
import com.example.ingda.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    MemberResponseDto memberToResponseDto(Member member);
}
