package com.example.ingda.domain.diary.service;

import com.example.ingda.common.exception.CustomException;
import com.example.ingda.common.exception.ErrorCode;
import com.example.ingda.domain.chatgpt.ChatgptService;
import com.example.ingda.domain.chatgpt.dto.ResponseChatGptDto;
import com.example.ingda.domain.diary.dto.ReviewRequestDto;
import com.example.ingda.domain.member.entity.Member;
import com.example.ingda.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ChatgptService chatgptService;
    private final MemberRepository memberRepository;

    public ResponseChatGptDto getReviewFromChatgpt(Long memberId, ReviewRequestDto requestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
        );
        if(member.getReviewCount() == 0){
            throw new CustomException(ErrorCode.REVIEW_COUNT_LIMIT);
        }

        ResponseEntity<ResponseChatGptDto>  responseEntity = chatgptService.sendToChatgpt(requestDto.getContent());

        member.minusReviewCount();
        return responseEntity.getBody();

    }

}
