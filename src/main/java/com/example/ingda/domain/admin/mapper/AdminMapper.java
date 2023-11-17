package com.example.ingda.domain.admin.mapper;

import com.example.ingda.domain.admin.dto.AdminMemberResponseDto;
import com.example.ingda.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    @Mapping(source = "inactive", target = "inactive")
    AdminMemberResponseDto memberToResponseDto(Member member);

    @Mapping(source = "inactive", target = "inactive")
    List<AdminMemberResponseDto> memberListToResponseDtoList(List<Member> memberList);
}
