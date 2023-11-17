package com.example.ingda.domain.admin.controller;

import com.example.ingda.common.MessageCode;
import com.example.ingda.common.ResponseMessage;
import com.example.ingda.domain.admin.dto.AdminMemberRequestDto;
import com.example.ingda.domain.admin.dto.AdminMemberResponseDto;
import com.example.ingda.domain.admin.service.AdminService;
import com.example.ingda.domain.member.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping(value = "/admin/members")
    public ResponseMessage<List<AdminMemberResponseDto>> getMemberInfo(@RequestParam(name = "nickname", required = false) String nickname,
                                                                       @RequestParam(name = "email", required = false) String email){
        List<AdminMemberResponseDto> allMemberInfo = adminService.getAllMemberInfo(nickname, email);
        return new ResponseMessage<>(MessageCode.SUCCESS, adminService.getAllMemberInfo(nickname, email));
    }

    @PutMapping(value = "/admin/members/{memberId}")
    public ResponseMessage<?> updateMemberByAdmin(@PathVariable(name = "memberId")Long memberId
                                                , @RequestBody AdminMemberRequestDto adminMemberRequestDto){
        adminService.updateMemberByAdmin(memberId, adminMemberRequestDto);
        return new ResponseMessage<>(MessageCode.SUCCESS, null);
    }

    @PostMapping(value = "/admin/member")
    public ResponseMessage<?> createMember(@RequestBody @Valid MemberRequestDto memberRequestDto){
        adminService.createMember(memberRequestDto);
        return new ResponseMessage<>(MessageCode.SUCCESS, null);
    }

    @DeleteMapping(value = "/admin/members/{memberId}")
    public ResponseMessage<?> deleteMember(@PathVariable(name = "memberId")Long memberId){
        adminService.deleteMember(memberId);
        return new ResponseMessage<>(MessageCode.SUCCESS, null);
    }

}
