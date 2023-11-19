package com.example.ingda.domain.diary.controller;

import com.example.ingda.domain.chatgpt.dto.ResponseChatGptDto;
import com.example.ingda.domain.diary.dto.ReviewRequestDto;
import com.example.ingda.domain.diary.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/diary/review")
    public ResponseChatGptDto test(@RequestBody ReviewRequestDto reviewRequestDto){
        return reviewService.getReviewFromChatgpt(reviewRequestDto);
    }
}
