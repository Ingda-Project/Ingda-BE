package com.example.ingda.domain.admin.mapper;

import com.example.ingda.domain.admin.dto.AdminMemberResponseDto;
import com.example.ingda.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    AdminMemberResponseDto memberToResponseDto(Member member);

    List<AdminMemberResponseDto> memberListToResponseDtoList(List<Member> memberList);
}
