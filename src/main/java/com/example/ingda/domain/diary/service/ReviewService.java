package com.example.ingda.domain.diary.service;

import com.example.ingda.domain.chatgpt.ChatgptService;
import com.example.ingda.domain.chatgpt.dto.ResponseChatGptDto;
import com.example.ingda.domain.diary.dto.ReviewRequestDto;
import com.example.ingda.domain.diary.dto.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ChatgptService chatgptService;

    public ResponseChatGptDto getReviewFromChatgpt(ReviewRequestDto requestDto) {
        return chatgptService.sendToChatgpt(requestDto.getContent());

    }

}
