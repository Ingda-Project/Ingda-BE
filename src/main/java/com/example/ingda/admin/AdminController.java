package com.example.ingda.admin;

import com.example.ingda.common.MessageCode;
import com.example.ingda.common.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping(value = "/admin/members")
    public ResponseMessage<?> getMemberInfo(){
        return new ResponseMessage<>(MessageCode.SUCCESS, adminService.getAllMemberInfo());
    }

}
