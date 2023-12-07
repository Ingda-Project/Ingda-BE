package com.example.ingda.domain.member.service;

import com.example.ingda.common.ResponseMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public interface SocialService {
    ResponseEntity<ResponseMessage> login(String code, HttpServletResponse response) throws JsonProcessingException;
}
