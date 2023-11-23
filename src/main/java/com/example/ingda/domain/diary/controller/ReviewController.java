package com.example.ingda.domain.diary.controller;

import com.example.ingda.domain.diary.dto.ReviewRequestDto;
import com.example.ingda.domain.diary.dto.ReviewResponseDto;
import com.example.ingda.domain.diary.service.ReviewService;
import com.example.ingda.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/diary/review")
    public ReviewResponseDto getReview(@AuthenticationPrincipal UserDetailsImpl userDetail, @RequestBody ReviewRequestDto reviewRequestDto){
        return reviewService.getReviewFromChatgpt(userDetail.getMember().getMemberId(), reviewRequestDto);
    }
}
